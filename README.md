# Chisel Workspace

My workspace to explore Chisel and its capability in digital design.

## Installation - Ubuntu
1. Utility download:

```bash
sudo apt-get curl
sudo apt-get build-essential
```

2. Install Java:
```bash
sudo apt-get install default-jdk
```

3. Scala download:
Follow from website: https://www.scala-sbt.org/download.html

```bash
echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | sudo tee /etc/apt/sources.list.d/sbt.list
echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | sudo tee /etc/apt/sources.list.d/sbt_old.list
curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | sudo apt-key add
sudo apt-get update
sudo apt-get install sbt
```
4. (Optional) Install Verilator
Follow from website: https://verilator.org/guide/latest/install.html

Prequisites:
```bash
sudo apt-get install git perl python3 make autoconf g++ flex bison ccache
sudo apt-get install libgoogle-perftools-dev numactl perl-doc
sudo apt-get install libfl2  # Ubuntu only (ignore if gives error)
sudo apt-get install libfl-dev  # Ubuntu only (ignore if gives error)
sudo apt-get install zlibc zlib1g zlib1g-dev  # Ubuntu only (ignore if gives error)
```
Clone Verilator:
```bash
git clone https://github.com/verilator/verilator
# Every time you need to build:
# unsetenv VERILATOR_ROOT  # For csh; ignore error if on bash
unset VERILATOR_ROOT  # For bash
cd verilator
git pull         # Make sure git repository is up-to-date
git tag          # See what versions exist
#git checkout master      # Use development branch (e.g. recent bug fixes)
#git checkout stable      # Use most recent stable release
#git checkout v{version}  # Switch to specified release version

autoconf         # Create ./configure script
./configure      # Configure and create Makefile
make             # Compile and build Verilator
sudo make install
```



## Contributing
Pham Laboratory (Integrated circuit design laboratory)- The University of Electro-Communications (UEC), Tokyo, Japan.

## License

[MIT](https://choosealicense.com/licenses/mit/)
