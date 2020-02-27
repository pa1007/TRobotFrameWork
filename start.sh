#!/bin/bash
jar=""
getinfot() {
  NAME=$1

  content=$(wget https://pa1007.dev/api/checksum/"$NAME" -q -O -)

  IFS='@'
  read -ra ADDR <<<"$content"
  i=""
  ur=""
  nam=""
  for z in "${ADDR[@]}"; do # access each element of array
    if [ "$i" = "" ]; then
      i=$z
    elif [ "$ur" = "" ]; then
      ur=$z
    else
      nam=$z
    fi
  done
  if [ "$NAME" = "TRobotFrameWork" ]; then
    des="app/"
    jar=$nam
  else
    des="app/modules/"
  fi

  if test -f "$i"; then
    echo "$i exist, $nam est à jour !"
  else
    touch "$i"
    echo "téléchargement de $nam dans le fichier $des"
    wget -P $des "$ur"
  fi
}

getinfot TRobotFrameWork
getinfot ModuleMouvement
getinfot ModuleGyroscopique
wait 12
echo "Excecution du frawework $jar"
sudo java -jar "$jar"
