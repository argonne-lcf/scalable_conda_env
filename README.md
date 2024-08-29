# Scalable conda environment
This is to show how to setup conda environment for large scale runs

1. Create tarball for software environment. We suggest you do not do compression, just pack everything 

   ```bash
      tar -cvf pyenvs.tar.gz pyenvs
   ```

2. Transfer the tarball to all the local storage and decompress it

   ```bash
   module load frameworks
   # Get the number of nodes
   NODES=$(cat $PBS_NODEFILE | uniq | wc -l) 
   mpiexec -np $NODES --ppn 1 python ./cache_soft.py --src pyenvs.tar.gz --dst /tmp/pyenvs.tar.gz --d
   conda activate /tmp/pyenvs
   ```

Example submission script can be found here: [qsub.sc](./qsub.sc)

