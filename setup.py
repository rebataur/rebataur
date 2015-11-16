from setuptools import setup, find_packages
setup(
  name='rebfdw',
  version='0.0.1',
  author='Priya Ranjan',
  license='Mozilla Public License',
  packages=['reb_fdw','reb_main'],
  install_requires=[
          'psycopg2',
	  'multicorn',
	  'tweepy',
	  'pyowm',
	  'bottle'
      ],
)
