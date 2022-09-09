#!/bin/bash
# -*- ENCODING: UTF-8 -*-
brew install sphinx-doc
python3 -m venv .venv
source .venv/bin/activate
python3 -m pip install sphinx
sphinx-build --version
pip install furo
pip install pandas
pip install lxml
pip install tqdm
cd NdbCareWeb
cd docs
make html 
open build/html/index.html

