trait Item {
  val danioQueProduce: Double = 0
  def esArma(): Boolean = false
}

case class Arma (override val danioQueProduce: Double) extends Item {
  override def esArma(): Boolean = true
}

case class Comestible (calorias: Double) extends Item {
  def hambreQueDisminuye: Double = calorias
}

case object SistemaDeVuelo extends Item