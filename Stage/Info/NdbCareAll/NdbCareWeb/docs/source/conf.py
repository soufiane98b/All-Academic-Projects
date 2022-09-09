# Configuration file for the Sphinx documentation builder.
#
# For the full list of built-in configuration values, see the documentation:
# https://www.sphinx-doc.org/en/master/usage/configuration.html
# If extensions (or modules to document with autodoc) are in another directory,
# add these directories to sys.path here.
import pathlib
import sys
import os
#sys.path.insert(0, pathlib.Path('/Users/soufiane/Desktop/Stage/Info/NdbCareAll/NdbCareWeb/docs/source').parents[2].resolve().as_posix())
sys.path.insert(0, pathlib.Path(os.getcwd()+'/source').parents[2].resolve().as_posix())


# -- Project information -----------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#project-information

project = 'NdbCareWeb'
copyright = '2022, Boustique Soufiane, Ferrat Samy'
author = 'Boustique Soufiane, Ferrat Samy'
release = '0.1'

# -- General configuration ---------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#general-configuration

extensions = [
    'sphinx.ext.duration',
    'sphinx.ext.doctest',
    'sphinx.ext.autodoc',
]

templates_path = ['_templates']
exclude_patterns = []



# -- Options for HTML output -------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#options-for-html-output

html_theme = 'furo'
html_static_path = ['_static']
