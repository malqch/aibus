#!/bin/bash
flag="$1"
ip="$2"
port="$3"
username="$4"
password="$5"
sport="$6"

#echo ${flag} >> /data/ec/scripts/xxx.log
#echo ${ip} >> /data/ec/scripts/xxx.log
#echo ${port} >> /data/ec/scripts/xxx.log
#echo ${username} >> /data/ec/scripts/xxx.log
#echo ${password} >> /data/ec/scripts/xxx.log
#echo ${sport} >> /data/ec/scripts/xxx.log

if [ "${flag}" = "open" ]; then
  #echo '11111111111111111111'  >> /data/ec/scripts/xxx.log
  count=`ps -aux | grep ':localhost:7001' | grep -v grep | wc -l`
  if [ "${count}" -eq "0" ];then
    
    #echo '222222222222' >> /data/ec/scripts/xxx.log
        /usr/bin/expect << EOF
        set time 30
        spawn ssh -p ${port} -fCNR ${sport}:localhost:7001 ${username}@${ip}
        expect {
            "*yes/no*" {send "yes\r"; exp_continue }
            "*password:" { send "${password}\r" }
        }
        expect eof
EOF
  fi
elif [ "${flag}" = "close" ]; then
    #echo '333333333333' >> /data/ec/scripts/xxx.log
    kill -9 `ps -aux | grep ':localhost:7001' | grep -v grep | awk '{print $2}'`
fi 

#echo '444444444444' >> /data/ec/scripts/xxx.log
