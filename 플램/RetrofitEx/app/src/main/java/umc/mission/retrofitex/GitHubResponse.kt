package umc.mission.retrofitex

import java.security.acl.Owner

data class GitHubResponse(
    val id : Int,
    val node_id : String,
    val name : String,
    val fullName : String,
    val private : Boolean,
    val owner: umc.mission.retrofitex.Owner
)

data class Owner(
    val avatar_url: String,
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val html_url: String,
    val id: Int,
    val login: String,
    val node_id: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val url: String
)

class RepoResponse: ArrayList<GitHubResponse>()