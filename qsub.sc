#!/bin/sh
#PBS -l walltime=0:30:00
#PBS -A Aurora_deployment
#PBS -q lustre_scaling
#PBS -l select=2
#PBS -l filesystems=flare:home
#PBS -N scalable_conda


cd ${PBS_O_WORKDIR}

module load frameworks
export PBS_JOBSIZE=$(cat $PBS_NODEFILE | uniq | wc -l)
echo "Transfer built python package ($BUILD): `date`"
mpiexec --pmi=pmix -np $PBS_JOBSIZE --ppn 1 python cache_soft.py \
      --src /flare/Aurora_deployment/AuroraGPT/build/2024-08-13/anl_2024_q3_soft.tar.gz \
      --dst /tmp/anl_2024_q3_soft.tar.gz --d

conda activate /tmp/anl_2024_q3-official_release
echo "I am using conda from: "
which python

echo "Test the environment"
mpiexec --pmi=pmix -np $((PBS_JOBSIZE*12)) --ppn 12 python -c 'import torch; print(torch.__file__)'
