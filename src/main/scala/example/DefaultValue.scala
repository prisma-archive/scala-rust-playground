package example

import play.api.libs.json.Json.DefaultValues

sealed trait DefaultValue[T] {
  def default: T
}
object DefaultValues {
  object NullDefaultValue             extends DefaultValue[Null]    { def default = null  }
  implicit object IntDefaultValue     extends DefaultValue[Int]     { def default = 0     }
  implicit object BooleanDefaultValue extends DefaultValue[Boolean] { def default = false }

  implicit def nullDefault[T >: Null]: DefaultValue[T] = NullDefaultValue.asInstanceOf[DefaultValue[T]]
}
