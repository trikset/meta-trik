export LD_LIBRARY_PATH=/home/root/trik/:$LD_LIBRARY_PATH
export TRIK_PYTHONPATH=$(python3 -c 'import sys; import os; print(os.pathsep.join(sys.path))')
