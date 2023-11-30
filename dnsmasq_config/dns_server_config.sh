#!/usr/bin/env bash
#/home/parsac/AudioVisuales/Practica_AudioVisuales/dnsmasq_config/dnsmasq.conf
#/home/parsac/AudioVisuales/Practica_AudioVisuales/dnsmasq_config/ports.conf
# dir for dns /tmp/pycore.1/dns-server.conf/etc.dnsmasq.d/

# echo "nameserver 127.0.0.1" >> /etc/dnsmasq.d/resolv.conf
# cp  /home/parsac/AudioVisuales/Practica_AudioVisuales/dnsmasq_config/dnsmasq.conf /etc/dnsmasq.d

touch /etc/dnsmasq.d/hosts

echo '127.0.0.1 localhost
10.0.1.10 catalogue-server
10.0.2.20 video-server' >> /etc/dnsmasq.d/hosts

touch /etc/dnsmasq.d/resolv.conf
 
echo 'nameserver 127.0.0.1' >> /etc/dnsmasq.d/resolv.conf

touch /etc/dnsmasq.d/dnsmasq.conf

echo 'no-resolv
server=127.0.0.1
addn-hosts=/etc/dnsmasq.d/hosts
resolv-file=/etc/dnsmasq.d/resolv.conf' >> /etc/dnsmasq.d/dnsmasq.conf

killall dnsmasq
/usr/sbin/dnsmasq -C /etc/dnsmasq.d/dnsmasq.conf