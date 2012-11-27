role :deploy, "pertti.dyndns.info"
default_run_options[:pty] = true

default_environment['JAVA_HOME']="/usr/local/java"
default_environment['GROOVY_HOME']="/usr/local/groovy"
default_environment['GRAILS_HOME']="/home/heikki/grails"
default_environment['PATH'] = "$JAVA_HOME/bin:$GROOVY_HOME/bin:$GRAILS_HOME/bin:$PATH"

grails_cmd = "/home/heikki/grails/bin/grails -plain-output"

desc "Deploy latest version from git"
task :deploy do
  run "cd wirc && #{grails_cmd} clean"
  run "cd wirc && git pull"
  run "cd wirc && #{grails_cmd} prod war target/wirc.war"
  pid = capture("ps -eo pid,command | grep 'tomcat/bin' | grep -v grep | awk '{print $1}'")
  if(pid != '')
    sudo "kill -9 " + pid
  end
  run "rm /usr/local/tomcat/webapps/wirc.war; true"
  sudo "rm -rf /usr/local/tomcat/webapps/wirc"
  run "cp wirc/target/wirc.war /usr/local/tomcat/webapps/"
  sudo "nohup service tomcat start"
end

desc "Current revision on server"
task :revision do
  rev = capture('cd wirc && git rev-parse HEAD')
  puts rev
end

desc "Current grails version on server"
task :grails_version do
  puts capture("#{grails_cmd} -version")
end
