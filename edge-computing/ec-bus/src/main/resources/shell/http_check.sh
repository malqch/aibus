#!/usr/bin/env bash
#use: Health status monitoring script
#version: 2.0
update_path="/data/ec/updata/"
start_command='/data/jdk1.8.0_231/bin/java -Xms768m -Xmx768m -Xmn512m -XX:MetaspaceSize=100m -XX:MaxMetaspaceSize=256m -XX:SurvivorRatio=10 -XX:+UseParallelGC -XX:+UseParallelOldGC -XX:-UseAdaptiveSizePolicy -XX:+DisableExplicitGC -jar "/data/ec/software/ec.jar" > /dev/null 2>&1 &' #start commandprocess_name="java.*"			#process name
process_name="java"			#process name
http_check_url="http://127.0.0.1:7001/gateway/ping"	#http Healthy URL
#Monitoring for updates function
updateing_judge(){
#parameter info 
#$1 :update_path
#$2 :update_lock
update_lock=`find ${1}  -name '*.lock'`
if [ -n "${update_lock}" ];then
	exit 3
fi
}
#monitoring process
Process_survival(){
#parameter info
#$1 :process_name
#$2 :start_command
#Determine whether the number of processes is 1
NUM=`ps aux | grep ${1} | grep -v grep |wc -l`
if [ "$NUM" -ge "2" ];then
	#stop
	/usr/bin/killall -9 ${1}
	exit 3
elif [ "$NUM" -eq "1" ];then
	#no active
	:
else
	#start 
	eval $2  
	exit 0
fi
}

#Dead monitoring process
DM_process(){
#paramenter info
#$1 :process name
#Judge whether the process is dead
NUM_STAT=`ps aux | grep ${1} | grep T | grep -v grep | wc -l`
if [ "$NUM_STAT" -gt "0" ];then
	#stop
	/usr/bin/killall -9 ${1}
	exit 3
fi
}
#Whether the HTTP monitoring program survives
http_check(){
#paramenter info
#$1 :http_check URL
#$2 :process_name
http=0
for i in {1..3}
do
	/usr/bin/curl -I  -m 3 ${1} 
	#-m The unit of time is second. 
	#-I only http head
	#-G get Method request
	if [ "$?" -eq "0" ];then
		:
	else 
	 	http_check=$((http+1))
	fi
done
if [ "$http" -eq "3" ];then
        /usr/bin/killall -9 ${2}  #进程名使用变量
	exit 3
fi
}

##
#
#
#
#Subjective logic
	updateing_judge ${update_path} ${update_lock}
	if [ "$?" !=  "0" ];then
		exit 4
	fi
	Process_survival ${process_name} "$start_command"
	DM_process ${process_name}
	updateing_judge ${update_path} ${update_lock}
        if [ "$?" !=  "0" ];then
                exit 4
        fi
	http_check ${http_check_url} ${process_name}


