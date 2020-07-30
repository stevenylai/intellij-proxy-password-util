# IntellJ Proxy Password Util

Chances are that if you are behind a corporate firewall, you need to change password periodically.

This tool can be used to update/view your proxy credentials.

To build:

`sbt package assembly`

To view credential

`java -jar intellij-proxy-password-util-assembly.jar /path/to/proxy.settings.pwd` 

For example:

`java -jar intellij-proxy-password-util-assembly.jar C:\Users\stevenylai\AppData\Roaming\JetBrains\PyCharm2020.1\options\proxy.settings.pwd`

To set credential

`java -jar intellij-proxy-password-util-assembly.jar /path/to/proxy.settings.pwd newpass` 

For example:

`java -jar intellij-proxy-password-util-assembly.jar C:\Users\stevenylai\AppData\Roaming\JetBrains\PyCharm2020.1\options\proxy.settings.pwd newPass`