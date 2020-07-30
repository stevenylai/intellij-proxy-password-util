import java.nio.file.Paths

import org.junit._
import org.junit.Assert.assertEquals

class PasswordUtilSuite {
  val path = getClass.getResource("proxy.settings.pwd").toURI

  @Test def `check sample file`(): Unit = {
    println("Sample file " + path)
    val pathVal = Paths.get(path)
    val props = PasswordUtil.load(pathVal)
    val username = props.getProperty("proxy.login", null)
    val password = props.getProperty("proxy.password", null)
    assertEquals("slai061217", username)
    assertEquals("Jun#20#Jun", password)
  }
  @Test def `check save sample file`(): Unit = {
    val pathVal = Paths.get(path)
    val props = PasswordUtil.load(pathVal)
    val existingPass = props.getProperty("proxy.password", null)
    // Set new password
    props.setProperty("proxy.password", "simple")
    PasswordUtil.store(props, pathVal)
    val newProps = PasswordUtil.load(pathVal)
    assertEquals("simple", newProps.getProperty("proxy.password", null))
    // Restore to original
    props.setProperty("proxy.password", existingPass)
    PasswordUtil.store(props, pathVal)
    val restoredProps = PasswordUtil.load(pathVal)
    assertEquals(existingPass, restoredProps.getProperty("proxy.password", null))
  }
}
