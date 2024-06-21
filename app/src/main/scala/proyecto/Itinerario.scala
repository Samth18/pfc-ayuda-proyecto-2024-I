package proyecto

class Itinerario {

  type aeropuertos = List[Aeropuerto]
  type vuelos = List[Vuelo]

  def itinerarios(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[List[Vuelo]] = {

    val vuelosPorOrigen = vuelos.groupBy(_.Org.toLowerCase) // En este apartado constrimos un mapa de adyacencia, lo agrupamos y lo convertimos a minúsculas para asegurar uniformidad.

    def buscarItinerarios(origen: String, destino: String, visitados: Set[String], rutaActual: List[Vuelo]): List[List[Vuelo]] = {
      if (origen == destino) {
        List(rutaActual)
      } else if (visitados.contains(origen)) {
        List()
      } else {
        val vuelosDesdeOrigen = vuelosPorOrigen.getOrElse(origen.toLowerCase, List())
        for {
          vuelo <- vuelosDesdeOrigen
          itinerario <- buscarItinerarios(vuelo.Dst.toLowerCase, destino.toLowerCase, visitados + origen, rutaActual :+ vuelo)
        } yield itinerario
      }
    }


    (cod1: String, cod2: String) => buscarItinerarios(cod1.toLowerCase, cod2.toLowerCase, Set(), List())
  }





  def itinerariosTiempo(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[List[Vuelo]] = {

    val buscarItinerariosFn = itinerarios(vuelos, aeropuertos)

    def gmtOffset(cod: String): Double = {
      aeropuertos.find(_.Cod.equalsIgnoreCase(cod)).map(_.GMT).getOrElse(0.0)
    } //Es una función interna, que calcula y retorna el desfase horario GMT de un aeropuerto de acuerdo a su código.


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
      itinerariosPosibles.sortBy(calcularDuracionTotal).take(3)
    }
  }

  def itinerariosEscalas(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[List[Vuelo]] = {
    val buscarItinerariosFn = itinerarios(vuelos, aeropuertos)

    def calcularNumeroEscalas(itinerario: List[Vuelo]): Int = {
      itinerario.map(_.Esc).sum + (itinerario.length - 1) // La cantidad de escalas es la suma de las escalas técnicas más los cambios de vuelo
    }

    (cod1: String, cod2: String) => {
      val itinerariosPosibles = buscarItinerariosFn(cod1.toLowerCase, cod2.toLowerCase)

      itinerariosPosibles.sortBy(calcularNumeroEscalas).take(3)
    }
  }

  def itinerariosAire(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[List[Vuelo]] = {
    val buscarItinerariosAire = itinerarios(vuelos, aeropuertos)

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
      itinerariosPosibles.sortBy(calcularDuracionTotal).take(3)
    }
  }

  def itinerariosSalida(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String, Int, Int) => List[Vuelo] = {
    val buscarItinerariosFn = itinerarios(vuelos, aeropuertos)

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
      // Considerar el caso de itinerarios que cruzan medianoche
      horaLlegada <= tiempoCita || (horaLlegada < 1440 && tiempoCita < horaLlegada)
    }

    (origen: String, destino: String, horaCita: Int, minCita: Int) => {
      val tiempoCita = convertirAMinutos(horaCita, minCita)
      val todosItinerarios = buscarItinerariosFn(origen, destino)
      val itinerariosValidos = todosItinerarios.filter(it => esValido(it, tiempoCita))

      val itinerariosOrdenados = itinerariosValidos.sortBy { it =>
        val horaLlegada = calcularHoraLlegadaTotal(it)
        val lapsoTiempo = calcularLapsoTiempo(horaLlegada, tiempoCita)
        (lapsoTiempo, -calcularHoraSalidaTotal(it))
      }

      itinerariosOrdenados.headOption.getOrElse(List.empty)
    }
  }

}