package umc.mission.floclone

interface SignUpView {
    fun onSignUpSuccess()
    fun onSignUpFailure(respMessage: String)
}