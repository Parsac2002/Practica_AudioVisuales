#!/usr/bin/env bash
echo "nameserver 10.0.0.10" > /etc/dnsmasq.d/resolv.conf
cp  /home/parsac/AudioVisuales/Practica_AudioVisuales/dnsmasq_config/dnsmasq.conf /etc/dnsmasq.d
killall dnsmasq
/usr/sbin/dnsmasq -C /etc/dnsmasq.d/dnsmasq.conf
# dir for client /tmp/pycore.1/client.conf/etc.dnsmasq.d