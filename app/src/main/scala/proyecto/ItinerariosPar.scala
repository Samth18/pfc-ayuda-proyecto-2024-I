package proyecto

import common._

import scala.collection.parallel.CollectionConverters._
import scala.collection.parallel.ParSeq
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._

class ItinerariosPar() {
  type aeropuertos = List[Aeropuerto]
  type vuelos = List[Vuelo]

  def itinerariosPar(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[List[Vuelo]] = {

    val vuelosPorOrigen = vuelos.groupBy(_.Org.toLowerCase) // Construimos un mapa de adyacencia, agrupando y convirtiendo a minúsculas para asegurar uniformidad.

    def buscarItinerarios(origen: String, destino: String, visitados: Set[String], rutaActual: List[Vuelo]): Future[List[List[Vuelo]]] = {
      if (origen == destino) {
        Future.successful(List(rutaActual))
      } else if (visitados.contains(origen)) {
        Future.successful(List())
      } else {
        val vuelosDesdeOrigen = vuelosPorOrigen.getOrElse(origen.toLowerCase, List())
        val futurosItinerarios = vuelosDesdeOrigen.map { vuelo =>
          buscarItinerarios(vuelo.Dst.toLowerCase, destino.toLowerCase, visitados + origen, rutaActual :+ vuelo)
        }

        Future.sequence(futurosItinerarios).map(_.flatten)
      }
    }

    (cod1: String, cod2: String) => {
      val futurosResultados = buscarItinerarios(cod1.toLowerCase, cod2.toLowerCase, Set(), List())

      import scala.concurrent.Await
      import scala.concurrent.duration._

      Await.result(futurosResultados, 10.seconds)
    }
  }

  def itinerariosTiempoPar(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[List[Vuelo]] = {

    val buscarItinerariosFn = itinerariosPar(vuelos, aeropuertos)

    def gmtOffset(cod: String): Double = {
      aeropuertos.find(_.Cod.equalsIgnoreCase(cod)).map(_.GMT).getOrElse(0.0)
    } // Es una función interna, que calcula y retorna el desfase horario GMT de un aeropuerto de acuerdo a su código.

    def calcularDuracionTotal(itinerario: List[Vuelo]): Int = {
      val duraciones = for {
        vuelo <- itinerario
        gmtOrigen = gmtOffset(vuelo.Org)
        gmtDestino = gmtOffset(vuelo.Dst)
      } yield {
        val horaSalidaGMT = vuelo.HS * 60 + vuelo.MS - ((gmtOrigen / 100) * 60).toInt
        val horaLlegadaGMT = vuelo.HL * 60 + vuelo.ML - ((gmtDestino / 100) * 60).toInt
        val duracionVuelo = horaLlegadaGMT - horaSalidaGMT
        if (duracionVuelo < 0) duracionVuelo + 1440 else duracionVuelo // Ajuste para vuelos nocturnos, en caso de que la duración del vuelo sea negativa.
      }

      val tiemposEspera = (itinerario zip itinerario.tail).map {
        case (vuelo1, vuelo2) =>
          val horaLlegadaGMT = vuelo1.HL * 60 + vuelo1.ML
          val horaSalidaSiguienteGMT = vuelo2.HS * 60 + vuelo2.MS
          val tiempoEspera = horaSalidaSiguienteGMT - horaLlegadaGMT
          if (tiempoEspera < 0) tiempoEspera + 1440 else tiempoEspera // Ajuste para espera nocturna
      }

      duraciones.sum + tiemposEspera.sum
    }

    (cod1: String, cod2: String) => {
      val itinerariosPosibles = buscarItinerariosFn(cod1, cod2)

      // Transformar cada cálculo en un Future y luego hacer una secuencia de esos futures
      val futurosDuraciones = itinerariosPosibles.map { itinerario =>
        Future {
          calcularDuracionTotal(itinerario) -> itinerario
        }
      }

      // Convertir la lista de futuros en un futuro de lista
      val futureResultados = Future.sequence(futurosDuraciones)

      // Aquí es donde hacemos el bloqueo para obtener el resultado
      val resultados = Await.result(futureResultados, 10.seconds)
      val itinerariosConDuracion = resultados.map { case (duracion, itinerario) =>
        itinerario
      }

      itinerariosConDuracion.sortBy(calcularDuracionTotal).take(3)
    }
  }

  def itinerariosEscalasPar(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[List[Vuelo]] = {
    val buscarItinerariosFn = itinerariosPar(vuelos, aeropuertos)

    def calcularNumeroEscalas(itinerario: List[Vuelo]): Int = {
      itinerario.map(_.Esc).sum + (itinerario.length - 1) // La cantidad de escalas es la suma de las escalas técnicas más los cambios de vuelo
    }

    (cod1: String, cod2: String) => {
      val itinerariosPosibles = buscarItinerariosFn(cod1.toLowerCase, cod2.toLowerCase)

      // Transformar cada cálculo en un Future y luego hacer una secuencia de esos futures
      val futurosEscalas = itinerariosPosibles.map { itinerario =>
        Future {
          calcularNumeroEscalas(itinerario) -> itinerario
        }
      }

      // Convertir la lista de futuros en un futuro de lista
      val futureResultados = Future.sequence(futurosEscalas)

      // Aquí es donde hacemos el bloqueo para obtener el resultado
      val resultados = Await.result(futureResultados, 10.seconds)
      val itinerariosConEscalas = resultados.map { case (escalas, itinerario) =>
        itinerario
      }

      itinerariosConEscalas.sortBy(calcularNumeroEscalas).take(3)
    }
  }

  def itinerariosAirePar(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[List[Vuelo]] = {
    val buscarItinerariosAire = itinerariosPar(vuelos, aeropuertos)

    def gmtOffset(cod: String): Double = {
      aeropuertos.find(_.Cod.equalsIgnoreCase(cod)).map(_.GMT).getOrElse(0.0)
    }

    def calcularDuracionTotal(itinerario: List[Vuelo]): Int = {
      val duraciones = for {
        vuelo <- itinerario
        gmtOrigen = gmtOffset(vuelo.Org)
        gmtDestino = gmtOffset(vuelo.Dst)
      } yield {
        val horaSalidaGMT = vuelo.HS * 60 + vuelo.MS - ((gmtOrigen / 100) * 60).toInt
        val horaLlegadaGMT = vuelo.HL * 60 + vuelo.ML - ((gmtDestino / 100) * 60).toInt
        val duracionVuelo = horaLlegadaGMT - horaSalidaGMT
        if (duracionVuelo < 0) duracionVuelo + 1440 else duracionVuelo // Ajuste para vuelos nocturnos
      }

      duraciones.sum
    }

    (cod1: String, cod2: String) => {
      val itinerariosPosibles = buscarItinerariosAire(cod1, cod2)

      // Transformar cada cálculo en un Future y luego hacer una secuencia de esos futures
      val futurosDuraciones = itinerariosPosibles.map { itinerario =>
        Future {
          calcularDuracionTotal(itinerario) -> itinerario
        }
      }

      // Convertir la lista de futuros en un futuro de lista
      val futureResultados = Future.sequence(futurosDuraciones)

      // Aquí es donde hacemos el bloqueo para obtener el resultado
      val resultados = Await.result(futureResultados, 10.seconds)
      val itinerariosConDuracion = resultados.map { case (duracion, itinerario) =>
        itinerario
      }

      itinerariosConDuracion.sortBy(calcularDuracionTotal).take(3)
    }
  }

  def itinerariosSalidaPar(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String, Int, Int) => List[Vuelo] = {
    val buscarItinerariosFn = itinerariosPar(vuelos, aeropuertos)

    def convertirAMinutos(hora: Int, minutos: Int): Int = {
      hora * 60 + minutos
    }

    def calcularHoraLlegadaTotal(itinerario: List[Vuelo]): Int = {
      convertirAMinutos(itinerario.last.HL, itinerario.last.ML)
    }

    def calcularHoraSalidaTotal(itinerario: List[Vuelo]): Int = {
      convertirAMinutos(itinerario.head.HS, itinerario.head.MS)
    }

    def calcularLapsoTiempo(horaLlegada: Int, horaCita: Int): Int = {
      val diferencia = horaCita - horaLlegada
      if (diferencia >= 0) diferencia else 1440 + diferencia
    }

    def esValido(itinerario: List[Vuelo], tiempoCita: Int): Boolean = {
      val horaLlegada = calcularHoraLlegadaTotal(itinerario)
      horaLlegada <= tiempoCita || (horaLlegada < 1440 && tiempoCita < horaLlegada)
    }

    (origen: String, destino: String, horaCita: Int, minCita: Int) => {
      val tiempoCita = convertirAMinutos(horaCita, minCita)
      val todosItinerarios = buscarItinerariosFn(origen, destino)

      // Filtrar itinerarios de forma concurrente
      val futurosItinerariosValidos = Future.traverse(todosItinerarios) { itinerario =>
        Future {
          if (esValido(itinerario, tiempoCita)) Some(itinerario) else None
        }
      }

      // Convertir la lista de futuros en un futuro de lista
      val futurosResultados = futurosItinerariosValidos.map(_.flatten)

      // Aquí es donde hacemos el bloqueo para obtener el resultado
      val itinerariosValidos = Await.result(futurosResultados, 10.seconds)

      // Ordenar itinerarios de forma concurrente
      val futurosItinerariosOrdenados = Future {
        itinerariosValidos.sortBy { it =>
          val horaLlegada = calcularHoraLlegadaTotal(it)
          val lapsoTiempo = calcularLapsoTiempo(horaLlegada, tiempoCita)
          (lapsoTiempo, calcularHoraSalidaTotal(it))
        }
      }

      // Obtener el resultado ordenado
      val itinerariosOrdenados = Await.result(futurosItinerariosOrdenados, 10.seconds)

      itinerariosOrdenados.headOption.getOrElse(List.empty)
    }
  }

}
