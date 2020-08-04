import org.scalatest.{FreeSpec, Matchers}

class TorneoTest extends FreeSpec with Matchers {
  val hipo: Vikingo = Vikingo(2, 25, SistemaDeVuelo, 10)
  val astrid: Vikingo = Vikingo(15, 20, Arma(30), 10)
  val patan: Vikingo = Vikingo(20, 15, Arma(100), 5)

  val chimuelo: Dragon = FuriaNocturna(100, 100, SistemaDeVuelo)
  val nadderMortiferoRojo: Dragon = NadderMortifero(100)
  val gronckle: Dragon = Gronckle(60, 3)

  val festivalDeInvierno: Torneo = Torneo(List(Combate(Some(RequisitoBarbarosidadPosta(13))), Carrera(None, 11), Pesca(None)),
                                          List(chimuelo, nadderMortiferoRojo, gronckle))

  "Participacion Individual" - {
    "astrid gana el festival de invierno con regla Estandar" in {
      assertResult(Some(astrid.incrementarNivelDeHambre(5))) {
        festivalDeInvierno.realizarTorneoIndividualmente(List(hipo, astrid, patan, Patapez), new Estandar)
      }
    }

    "astrid gana el festival de invierno con regla de Eliminacion" in {
      assertResult(Some(astrid.incrementarNivelDeHambre(10))) {
        festivalDeInvierno.realizarTorneoIndividualmente(List(hipo, astrid, patan, Patapez), Eliminacion(1))
      }
    }

    "patan gana el festival de invierno con regla de Torneo Inverso" in {
      assertResult(Some(patan.incrementarNivelDeHambre(10))) {
        festivalDeInvierno.realizarTorneoIndividualmente(List(hipo, astrid, patan, Patapez), TorneoInverso)
      }
    }

    "patan gana el festival de invierno con regla de Ban de Dragones" in {
      assertResult(Some(patan.incrementarNivelDeHambre(10))) {
        festivalDeInvierno.realizarTorneoIndividualmente(List(hipo, astrid, patan, Patapez), BanDeDragones(dragon => dragon.peso < 60))
      }
    }

    "hipo gana el festival de invierno con Handicap" in {
      assertResult(Some(hipo.incrementarNivelDeHambre(5))) {
        festivalDeInvierno.realizarTorneoIndividualmente(List(hipo, astrid, patan, Patapez), Handicap)
      }
    }
  }

  "Participacion por Equipos" - {
    val astridYPatapez = Equipo(List(astrid, Patapez))
    val hipoYPatan = Equipo(List(hipo, patan))

    "el equipo de astridYPatapez gana el festival de invierno por equipos con solo Astrid en pie" in {
      assertResult(Some(Equipo(List(astrid.incrementarNivelDeHambre(5))))) {
        festivalDeInvierno.realizarTorneoPorEquipos(List(astridYPatapez, hipoYPatan))
      }
    }
  }
}
