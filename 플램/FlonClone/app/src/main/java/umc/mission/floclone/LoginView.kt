package umc.mission.floclone

interface LoginView {
    fun onLoginSuccess(code: Int, result: umc.mission.floclone.network.Result)
    fun onLoginFailure(code: Int)
}