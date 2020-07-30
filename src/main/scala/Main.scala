import java.nio.file.{FileSystem, FileSystems, Paths}

object Main {
  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      println("Usage : command <path> [password]")
    } else {
      val path = FileSystems.getDefault().getPath(args(0))
      val props = PasswordUtil.load(path)

      if (args.length > 1) {
        props.setProperty("proxy.password", args(1))
        PasswordUtil.store(props, path)
      } else {
        val username = props.getProperty("proxy.login", null);
        val password = props.getProperty("proxy.password", null);
        println("Proxy credential: " + username + ":" + password)
      }
    }
  }
}
