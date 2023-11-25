#!/usr/bin/env bash
cp /home/parsac/AudioVisuales/Practica_AudioVisuales/dnsmasq_config/Aud_page.conf /var/www
cp /home/parsac/AudioVisuales/Practica_AudioVisuales/dnsmasq_config/html/index.html /var/www
sudo service apache2 restart