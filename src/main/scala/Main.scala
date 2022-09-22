import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpRequest, HttpResponse, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import spray.json.enrichAny
import spray.json.RootJsonFormat
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Future
import scala.util.{Failure, Success}


case class User (
                          user_name: String,
                          display_name: String,
                          avatar: String,
                          geo_location: String,
                          email: Option[String] = None,
                          url: String = "null",
                          created_at: String,
                          repos: Array[Repos] = Array()
                        )
case class Repos (
                 name: String,
                 url: String
                 )

object Main extends FormatJson {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext

    val githubUserUrl = system.settings.config.getString("github.user.url")

    val userRoute: Route =
      path("github" / Remaining ) { user =>
        get {
          //val githubUser: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = githubUserUrl + user))
          //val githubRepos: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = githubUserUrl + user + "/repos"))
          val res = for {
            r1 <- {
              val githubUser: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = githubUserUrl + user))
              githubUser.flatMap {
                case HttpResponse(StatusCodes.OK, _, e, _) =>
                  Unmarshal(e).to[User]
              }
            }
            r2 <- {
              val githubRepos: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = githubUserUrl + user + "/repos"))
              githubRepos.flatMap {
                case HttpResponse(StatusCodes.OK, _, e, _) =>
                  Unmarshal(e).to[Array[Repos]]
              }
            }
          } yield (
            r1.copy(repos = r2).toJson
          )

          onComplete(res) {
            case Success(value) => {
              complete(s"$value")
            }
            case Failure(ex)    => {
              system.log.error(ex.getMessage, ex)
              complete(StatusCodes.InternalServerError, s"An error occurred: ${ex.getMessage}")
            }
          }
        }
      }
    val bindingFuture = Http().newServerAt("localhost", 8080).bind(userRoute)

  }
}