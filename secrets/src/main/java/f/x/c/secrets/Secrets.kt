package f.x.c.secrets

class Secrets {

    // Method calls will be added by gradle task hideSecret
    // Example : external fun getWellHiddenSecret(packageName: String): String

    companion object {
        init {
            System.loadLibrary("secrets")
        }
    }

    external fun getsecret(packageName: String): String

    external fun getiv(packageName: String): String
}