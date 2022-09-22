
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol.{jsonFormat2, jsonFormat3}
import spray.json.{DefaultJsonProtocol, DeserializationException, JsNumber, JsObject, JsString, JsValue, JsonFormat, NullOptions, RootJsonFormat, enrichAny}

trait FormatJson extends SprayJsonSupport with DefaultJsonProtocol with NullOptions {

  implicit val userFormat: RootJsonFormat[User] = new RootJsonFormat[User]{
    def write(obj: User): JsValue = {
      JsObject(
        "user_name" -> JsString(obj.user_name),
      "display_name" -> JsString(obj.display_name),
      "avatar" -> JsString(obj.avatar),
      "geo_location" -> JsString(obj.geo_location),
      "email" -> obj.email.toJson,
      "url" -> JsString(obj.url),
      "created_at" -> JsString(obj.created_at),
        "repos" -> obj.repos.toJson
      )
    }

    def read(json: JsValue): User = {
      val fields = json.asJsObject.fields
      User(
        fields("login").convertTo[String],
        fields("name").convertTo[String],
        fields("avatar_url").convertTo[String],
        fields("location").convertTo[String],
        fields.get("email").map(_.convertTo[Option[String]]).getOrElse(None),
        fields("url").convertTo[String],
        fields("created_at").convertTo[String],
        Array()
      )
    }
  }
  implicit val reposFormat: RootJsonFormat[Repos] = new RootJsonFormat[Repos]{
    def write(obj: Repos): JsValue = {
      JsObject(
        "name" -> JsString(obj.name),
        "url" -> JsString(obj.url)
      )
    }

    def read(json: JsValue): Repos = {
      val fields = json.asJsObject.fields
      Repos(
        fields("name").convertTo[String],
        fields("url").convertTo[String]
      )
    }
  }
}
