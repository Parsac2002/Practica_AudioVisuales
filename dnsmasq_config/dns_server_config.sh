#!/usr/bin/env bash
#/home/parsac/AudioVisuales/Practica_AudioVisuales/dnsmasq_config/dnsmasq.conf
#/home/parsac/AudioVisuales/Practica_AudioVisuales/dnsmasq_config/ports.conf
# dir for dns /tmp/pycore.1/dns-server.conf/etc.dnsmasq.d/
echo "nameserver 127.0.0.1" > /etc/dnsmasq.d/resolv.conf
cp  /home/parsac/AudioVisuales/Practica_AudioVisuales/dnsmasq_config/dnsmasq.conf /etc/dnsmasq.d
killall dnsmasq
/usr/sbin/dnsmasq -C /etc/dnsmasq.d/dnsmasq.conf