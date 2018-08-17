#!/bin/bash

set +x
set -e

function finish {
  popd
  docker-compose -f docker-compose.erd-generation.yml down --volumes
  rm erd.env
}
trap finish EXIT
pushd .

# prepare ERD folder
rm -Rf build/stockmanagement-erd
mkdir -p build/stockmanagement-erd

wget https://raw.githubusercontent.com/OpenLMIS/openlmis-ref-distro/master/settings-sample.env -O erd.env
docker-compose -f docker-compose.erd-generation.yml up -d
sleep 90
docker run --rm --network stockmanagement-net -v $(PWD)/build/stockmanagement-erd:/output schemaspy/schemaspy:snapshot -t pgsql -host db -port 5432 -db open_lmis -s stockmanagement -u postgres -p p@ssw0rd -I "(data_loaded)|(schema_version)|(jv_.*)" -norows -hq
cd build
zip -FSr stockmanagement-erd.zip stockmanagement-erd/
