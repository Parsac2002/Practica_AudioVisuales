#!/usr/bin/env bash
# dir for client /tmp/pycore.1/client.conf/etc.dnsmasq.d

# echo "nameserver 10.0.0.10" > /etc/dnsmasq.d/resolv.conf
# cp  /home/parsac/AudioVisuales/Practica_AudioVisuales/dnsmasq_config/dnsmasq.conf /etc/dnsmasq.d
# killall dnsmasq
# /usr/sbin/dnsmasq -C /etc/dnsmasq.d/dnsmasq.conf

touch /etc/dnsmasq.d/resolv.conf
 
echo 'nameserver 10.0.0.10' >> /etc/dnsmasq.d/resolv.conf

touch /etc/dnsmasq.d/dnsmasq.conf

echo 'resolv-file=/etc/dnsmasq.d/resolv.conf' >> /etc/dnsmasq.d/dnsmasq.conf

killall  dnsmasq
/usr/sbin/dnsmasq -C /etc/dnsmasq.d/dnsmasq.conf

#Despues de configurar el catalogue-server y el vide-server:
# wget http://catalogue-server
# wget http://video-server/movie.mp4