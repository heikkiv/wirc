role :deploy, "pertti.dyndns.info"
default_run_options[:pty] = true

desc "Deploy latest version from git"
task :deploy do
  run "cd wirc && git pull"
  run "cd wirc && grails war target/wirc.war"
  pid = capture("ps -eo pid,command | grep 'tomcat' | grep -v grep | awk '{print $1}'")
  if(pid != '')
    sudo "kill -9 " + pid
  end
  run "rm /usr/local/tomcat/webapps/wirc.war"
  run "rm -rf /usr/local/tomcat/webapps/wirc.war"
  run "cp wirc/target/wirc.war /usr/local/tomcat/webapps/"
  sudo "nohup service tomcat start"
end
