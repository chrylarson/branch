import Main.{arrayFormat, reposFormat, userFormat}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spray.json._

class FormatJsonTest extends AnyFlatSpec with Matchers {

  val user1 = "{\"login\": \"chrylarson\",\"id\": 928476,\"node_id\": \"MDQ6VXNlcjkyODQ3Ng==\",\"avatar_url\": \"https://avatars.githubusercontent.com/u/928476?v=4\",\"gravatar_id\": \"\",\"url\": \"https://api.github.com/users/chrylarson\",\"html_url\": \"https://github.com/chrylarson\",\"followers_url\": \"https://api.github.com/users/chrylarson/followers\",\"following_url\": \"https://api.github.com/users/chrylarson/following{/other_user}\",\"gists_url\": \"https://api.github.com/users/chrylarson/gists{/gist_id}\",\"starred_url\": \"https://api.github.com/users/chrylarson/starred{/owner}{/repo}\",\"subscriptions_url\": \"https://api.github.com/users/chrylarson/subscriptions\",\"organizations_url\": \"https://api.github.com/users/chrylarson/orgs\",\"repos_url\": \"https://api.github.com/users/chrylarson/repos\",\"events_url\": \"https://api.github.com/users/chrylarson/events{/privacy}\",\"received_events_url\": \"https://api.github.com/users/chrylarson/received_events\",\"type\": \"User\",\"site_admin\": false,\"name\": \"Chris R. Larson\",\"company\": null,\"blog\": \"https://chrislarson.me/\",\"location\": \"Minneapolis, MN\",\"email\": null,\"hireable\": null,\"bio\": \"AdTech and IoT\",\"twitter_username\": null,\"public_repos\": 32,\"public_gists\": 2,\"followers\": 9,\"following\": 1,\"created_at\": \"2011-07-20T18:03:32Z\",\"updated_at\": \"2022-04-19T10:22:57Z\"}"

  val repos1 = "[{\"name\": \"30days\",\"url\": \"https://api.github.com/repos/chrylarson/30days\"},{\"name\": \"30DaysOfHabit\",\"url\": \"https://api.github.com/repos/chrylarson/30DaysOfHabit\"}]"

  "An User JSON" should "convert to a User object" in {
    user1.parseJson.convertTo[User]
  }

  "An Repos JSON" should "convert to a Repos object" in {
    repos1.parseJson.convertTo[Array[Repos]]
  }
}
