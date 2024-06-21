package proyecto

import org.scalameter._
import proyecto.{Aeropuerto, Vuelo}

object Benchmarking {
  val itinerario = new Itinerario()
  val itinerarioPar = new ItinerariosPar()


  val vuelos = List(
    Vuelo("DL", 197, "TPA", 16, 10, "ATL", 17, 37, 0),
    Vuelo("DL", 208, "TPA", 11, 40, "BOS", 14, 24, 0),
    Vuelo("DL", 233, "DFW", 11, 40, "DEN", 12, 37, 0),
    Vuelo("DL", 817, "ATL", 18, 42, "DFW", 11, 40, 2),
    Vuelo("DL", 598, "ATL", 11, 47, "ORD", 12, 35, 0),
    Vuelo("DL", 347, "BOS", 6, 0, "SFO", 11, 10, 1),
    Vuelo("DL", 141, "LAX", 21, 0, "SFO", 12, 17, 0),
    Vuelo("DL", 178, "LAX", 10, 40, "ATL", 17, 48, 0),
    Vuelo("DL", 977, "ATL", 12, 9, "SFO", 14, 15, 0),
    Vuelo("DL", 575, "ORD", 13, 20, "ATL", 16, 7, 0),
    Vuelo("DL", 125, "ATL", 11, 47, "DFW", 14, 25, 1),
    Vuelo("DL", 970, "ATL", 19, 43, "DFW", 11, 39, 1),
    Vuelo("DL", 444, "DFW", 11, 53, "PHX", 13, 10, 0),
    Vuelo("DL", 748, "LAX", 10, 40, "DFW", 15, 28, 0),
    Vuelo("DL", 320, "MSP", 10, 55, "ATL", 14, 11, 0),
    Vuelo("DL", 940, "DTW", 12, 20, "DFW", 14, 3, 0),
    Vuelo("DL", 511, "BNA", 15, 50, "ATL", 17, 44, 0),
    Vuelo("DL", 299, "ATL", 11, 59, "DFW", 14, 15, 1),
    Vuelo("DL", 358, "DFW", 13, 7, "BOS", 17, 55, 0),
    Vuelo("DL", 726, "ATL", 10, 18, "PHL", 12, 15, 0),
    Vuelo("DL", 169, "BOS", 11, 5, "DFW", 13, 59, 0),
    Vuelo("DL", 803, "ATL", 8, 25, "DFW", 10, 55, 1),
    Vuelo("DL", 566, "HOU", 11, 40, "ATL", 14, 33, 0),
    Vuelo("DL", 964, "ATL", 10, 4, "DTW", 11, 50, 0),
    Vuelo("DL", 584, "DFW", 13, 16, "TPA", 16, 40, 0),
    Vuelo("DL", 401, "ORD", 6, 0, "ATL", 11, 8, 2),
    Vuelo("DL", 660, "DFW", 17, 0, "DCA", 10, 45, 0),
    Vuelo("DL", 262, "DFW", 13, 16, "DTW", 16, 55, 0),
    Vuelo("DL", 625, "ATL", 22, 31, "SEA", 13, 0, 1),
    Vuelo("DL", 30, "ATL", 12, 30, "JFK", 14, 38, 0),
    Vuelo("DL", 139, "DCA", 12, 40, "ATL", 14, 28, 0),
    Vuelo("DL", 755, "BOS", 11, 45, "ATL", 14, 26, 0),
    Vuelo("DL", 469, "ATL", 22, 22, "MIA", 13, 56, 0),
    Vuelo("DL", 583, "MSP", 9, 0, "TPA", 14, 35, 1),
    Vuelo("DL", 174, "DFW", 13, 7, "MIA", 16, 45, 0),
    Vuelo("DL", 684, "LAX", 15, 0, "SFO", 16, 11, 0),
    Vuelo("DL", 276, "TPA", 10, 0, "PHL", 13, 40, 1),
    Vuelo("DL", 582, "TPA", 21, 30, "ATL", 12, 47, 0),
    Vuelo("DL", 523, "DCA", 10, 45, "SEA", 14, 30, 1),
    Vuelo("DL", 919, "PHL", 5, 35, "ATL", 13, 0, 0),
    Vuelo("DL", 307, "ATL", 11, 44, "MSY", 12, 16, 0),
    Vuelo("DL", 163, "SFO", 13, 0, "LAX", 14, 15, 0),
    Vuelo("DL", 408, "DFW", 8, 6, "ATL", 12, 35, 1),
    Vuelo("DL", 991, "DCA", 19, 55, "MSY", 12, 50, 1),
    Vuelo("DL", 139, "MSY", 16, 25, "DFW", 17, 53, 0),
    Vuelo("DL", 694, "DFW", 23, 49, "MSY", 10, 5, 0),
    Vuelo("DL", 688, "DFW", 15, 0, "ATL", 18, 0, 0),
    Vuelo("DL", 703, "DFW", 11, 43, "SEA", 13, 40, 0),
    Vuelo("DL", 821, "DTW", 8, 45, "ATL", 10, 29, 0),
    Vuelo("DL", 991, "ATL", 22, 32, "MSY", 12, 50, 0),
    Vuelo("DL", 256, "ATL", 20, 59, "DTW", 12, 50, 0),
    Vuelo("DL", 472, "DFW", 11, 38, "MIA", 17, 15, 1),
    Vuelo("DL", 145, "ATL", 15, 10, "DFW", 16, 22, 0),
    Vuelo("DL", 581, "DFW", 8, 12, "ABQ", 10, 5, 0),
    Vuelo("DL", 387, "SFO", 15, 0, "LAX", 16, 11, 0),
    Vuelo("DL", 260, "DFW", 17, 58, "ATL", 12, 54, 2),
    Vuelo("DL", 390, "HOU", 6, 15, "DFW", 12, 4, 0),
    Vuelo("DL", 856, "LAX", 8, 0, "SFO", 11, 7, 0),
    Vuelo("DL", 645, "DTW", 16, 0, "DFW", 17, 50, 0),
    Vuelo("DL", 408, "HOU", 9, 40, "ATL", 12, 35, 0),
    Vuelo("DL", 996, "LAX", 12, 30, "BOS", 11, 46, 2),
    Vuelo("DL", 508, "DFW", 10, 41, "ATL", 14, 27, 1),
    Vuelo("DL", 474, "ATL", 23, 55, "TPA", 11, 0, 0),
    Vuelo("DL", 578, "TPA", 11, 15, "ATL", 12, 44, 0),
    Vuelo("DL", 539, "ATL", 11, 54, "TPA", 13, 20, 0),
    Vuelo("DL", 845, "ATL", 15, 27, "DFW", 17, 52, 1),
    Vuelo("DL", 436, "TPA", 9, 35, "ATL", 11, 2, 0),
    Vuelo("DL", 321, "ATL", 8, 19, "MIA", 10, 0, 0),
    Vuelo("DL", 356, "ATL", 15, 30, "BOS", 17, 52, 0),
    Vuelo("DL", 449, "ATL", 11, 44, "MIA", 13, 25, 0),
    Vuelo("DL", 558, "SEA", 12, 35, "MIA", 12, 15, 2),
    Vuelo("DL", 558, "SEA", 12, 35, "DFW", 19, 43, 1),
    Vuelo("DL", 588, "PHX", 12, 55, "DTW", 10, 30, 1),
    Vuelo("DL", 873, "ATL", 9, 7, "DFW", 11, 8, 1),
    Vuelo("DL", 333, "DFW", 22, 28, "ABQ", 13, 10, 0),
    Vuelo("DL", 470, "ATL", 18, 45, "PVD", 11, 0, 0),
    Vuelo("DL", 222, "PHX", 10, 30, "ATL", 16, 10, 0),
    Vuelo("DL", 464, "ATL", 11, 45, "DCA", 13, 25, 0),
    Vuelo("DL", 528, "DCA", 6, 50, "BOS", 10, 9, 0),
    Vuelo("DL", 1042, "ABQ", 6, 30, "DFW", 10, 1, 0),
    Vuelo("DL", 651, "MSY", 6, 0, "DFW", 13, 1, 0),
    Vuelo("DL", 820, "ATL", 10, 14, "ORD", 11, 5, 0),
    Vuelo("DL", 170, "LAX", 12, 15, "TPA", 10, 43, 1),
    Vuelo("DL", 946, "ATL", 11, 57, "HOU", 16, 3, 2),
    Vuelo("DL", 819, "ORD", 11, 55, "LAX", 17, 8, 2),
    Vuelo("FF", 37, "JFK", 20, 45, "MIA", 13, 40, 0),
    Vuelo("HP", 2, "PHX", 8, 0, "ORD", 12, 20, 0),
    Vuelo("HP", 44, "ATL", 8, 15, "LAX", 11, 25, 1),
    Vuelo("HP", 7, "JFK", 17, 20, "PHX", 10, 57, 0),
    Vuelo("HP", 38, "DEN", 18, 3, "LAX", 10, 30, 1),
    Vuelo("HP", 268, "PHX", 14, 33, "DCA", 11, 51, 1),
    Vuelo("HP", 862, "DFW", 17, 14, "PHX", 18, 46, 0),
    Vuelo("HP", 547, "PHX", 15, 37, "DFW", 19, 1, 0),
    Vuelo("HP", 594, "SFO", 11, 53, "PHX", 14, 44, 0),
    Vuelo("HP", 207, "PHX", 13, 7, "ABQ", 14, 15, 0),
    Vuelo("HP", 97, "DEN", 16, 50, "PHX", 18, 49, 0),
    Vuelo("HP", 836, "ABQ", 12, 30, "PHX", 13, 45, 0),
    Vuelo("HP", 636, "DFW", 19, 34, "PHX", 11, 3, 0),
    Vuelo("HP", 518, "SEA", 16, 0, "PHX", 19, 50, 0),
    Vuelo("HP", 864, "DCA", 16, 40, "PHX", 10, 59, 1)
  )

  val aeropuertos = List(
    Aeropuerto("ABQ", 195, 275, -800),
    Aeropuerto("ATL", 470, 280, -600),
    Aeropuerto("BNA", 430, 240, -700),
    Aeropuerto("BOS", 590, 100, -600),
    Aeropuerto("DCA", 540, 180, -600),
    Aeropuerto("DEN", 215, 205, -800),
    Aeropuerto("DFW", 310, 305, -700),
    Aeropuerto("DTW", 445, 140, -600),
    Aeropuerto("HOU", 330, 355, -700),
    Aeropuerto("JFK", 565, 130, -600),
    Aeropuerto("LAX", 55, 270, -900),
    Aeropuerto("MIA", 535, 390, -600),
    Aeropuerto("MSP", 340, 115, -700),
    Aeropuerto("MSY", 405, 345, -700),
    Aeropuerto("ORD", 410, 155, -700),
    Aeropuerto("PHL", 550, 155, -600),
    Aeropuerto("PHX", 120, 290, -800),
    Aeropuerto("PVD", 595, 122, -600),
    Aeropuerto("RDU", 530, 230, -600),
    Aeropuerto("SEA", 55, 45, -900),
    Aeropuerto("SFO", 10, 190, -900),
    Aeropuerto("STL", 380, 210, -700),
    Aeropuerto("TPA", 500, 360, -600)
  )

  def runItinerarioBenchmark(): Unit = {
    val time = config(
      Key.exec.minWarmupRuns := 10,
      Key.exec.maxWarmupRuns := 10,
      Key.exec.benchRuns := 10,   //Mas detalles sobre la ejecución
      //Key.verbose := true         //Si se requiere mas detalle en cuanto a la ejecución
    ) withWarmer new Warmer.Default measure {
      itinerario.itinerarios(vuelos, aeropuertos)("ATL", "LAX")
    }

    println(s"Tiempo promedio de ejecución de itinerarios: $time")
  }

  def runItinerariosParBenchmark(): Unit = {
    val time = config(
      Key.exec.minWarmupRuns := 10,
      Key.exec.maxWarmupRuns := 10,
      //Key.exec.benchRuns := 20,
      //Key.verbose := true
    ) withWarmer new Warmer.Default measure {
      itinerarioPar.itinerariosPar(vuelos, aeropuertos)("ATL", "LAX")
    }

    println(s"Tiempo promedio de ejecución de itinerariosPar: $time \n")
  }

  def runItinerariosTiempoBenchmark(): Unit = {
    val time = config(
      Key.exec.minWarmupRuns := 10,
      Key.exec.maxWarmupRuns := 10,
      //Key.exec.benchRuns := 20,
      //Key.verbose := true
    ) withWarmer new Warmer.Default measure {
     itinerario.itinerariosTiempo(vuelos, aeropuertos)("ATL", "LAX")
    }

    println(s"Tiempo promedio de ejecución de itinerariosTiempo: $time ")
  }

  def runItinerariosTiempoParBenchmark(): Unit = {
    val time = config(
      Key.exec.minWarmupRuns := 10,
      Key.exec.maxWarmupRuns := 10,
      //Key.exec.benchRuns := 20,
      //Key.verbose := true
    ) withWarmer new Warmer.Default measure {
      itinerarioPar.itinerariosTiempoPar(vuelos, aeropuertos)("ATL", "LAX")
    }

    println(s"Tiempo promedio de ejecución de itinerariosTiempoPar: $time \n")
  }

  def runItinerariosEscalasBenchmark(): Unit = {
    val time = config(
      Key.exec.minWarmupRuns := 10,
      Key.exec.maxWarmupRuns := 10,
      //Key.exec.benchRuns := 10,
      //Key.verbose := true
    ) withWarmer new Warmer.Default measure {
      itinerario.itinerariosEscalas(vuelos, aeropuertos)("ATL", "LAX")
    }

    println(s"Tiempo promedio de ejecución de itinerariosEscalas: $time ")
  }

  def runItinerariosEscalaParBenchmark(): Unit = {
    val time = config(
      Key.exec.minWarmupRuns := 10,
      Key.exec.maxWarmupRuns := 10,
      //Key.exec.benchRuns := 10,
      //Key.verbose := true
    ) withWarmer new Warmer.Default measure {
      itinerarioPar.itinerariosEscalasPar(vuelos, aeropuertos)("ATL", "LAX")
    }

    println(s"Tiempo promedio de ejecución de itinerariosEscalasPar: $time \n")
  }

  def runItinerariosAireBenchmark(): Unit = {
    val time = config(
      Key.exec.minWarmupRuns := 10,
      Key.exec.maxWarmupRuns := 10,
      //Key.exec.benchRuns := 10,
      //Key.verbose := true
    ) withWarmer new Warmer.Default measure {
      itinerarioPar.itinerariosTiempoPar(vuelos, aeropuertos)("ATL", "LAX")
    }

    println(s"Tiempo promedio de ejecución de itinerariosAire: $time")
  }

  def runItinerariosAireParBenchmark(): Unit = {
    val time = config(
      Key.exec.minWarmupRuns := 10,
      Key.exec.maxWarmupRuns := 10,
      //Key.exec.benchRuns := 10,
      //Key.verbose := true
    ) withWarmer new Warmer.Default measure {
      itinerarioPar.itinerariosAirePar(vuelos, aeropuertos)("ATL", "LAX")
    }

    println(s"Tiempo promedio de ejecución de itinerariosAirePar: $time \n")
  }

  def runItinerariosSalidaBenchmark(): Unit = {
    val time = config(
      Key.exec.minWarmupRuns := 10,
      Key.exec.maxWarmupRuns := 10,
      //Key.exec.benchRuns := 10,
      //Key.verbose := true
    ) withWarmer new Warmer.Default measure {
      itinerario.itinerariosSalida(vuelos, aeropuertos)("ATL", "LAX", 17, 7)
    }

    println(s"Tiempo promedio de ejecución de itinerariosSalida: $time")
  }

  def runItinerariosSalidaParBenchmark(): Unit = {
    val time = config(
      Key.exec.minWarmupRuns := 10,
      Key.exec.maxWarmupRuns := 10,
      //Key.exec.benchRuns := 10,
      //Key.verbose := true
    ) withWarmer new Warmer.Default measure {
      itinerarioPar.itinerariosSalidaPar(vuelos, aeropuertos)("ATL", "LAX", 17, 7)
    }

    println(s"Tiempo promedio de ejecución de itinerariosSalidaPar: $time")
  }

  def main(args: Array[String]): Unit = {

    runItinerarioBenchmark()
    runItinerariosParBenchmark()

    runItinerariosTiempoBenchmark()
    runItinerariosTiempoParBenchmark()

    runItinerariosEscalasBenchmark()
    runItinerariosEscalaParBenchmark()

    runItinerariosAireBenchmark()
    runItinerariosAireParBenchmark()

    runItinerariosSalidaBenchmark()
    runItinerariosSalidaParBenchmark()


  }
}
