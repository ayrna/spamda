<!-- left logo -->
![AYRNA SPAMDA logo](logo-ayrna-spamda.png)

<!-- Centered logo
<p align="center">
  <img src="logo-ayrna-spamda.png">
</p>
-->

---                                                                    
**SPAMDA**

Software for Pre-processing and Analysis of Meteorological DAta to build datasets

Copyright (c) 2017-2021 by **AYRNA Research Group**

[https://www.uco.es/grupos/ayrna/index.php/en](https://www.uco.es/grupos/ayrna/index.php/en)

Gómez-Orellana, A.M.; Fernández, J.C.; Dorado-Moreno, M.; Gutiérrez, P.A.; Hervás-Martínez, C. Building Suitable Datasets for Soft Computing and Machine Learning Techniques from Meteorological Data Integration: A Case Study for Predicting Significant Wave Height and Energy Flux. Energies 2021, 14, 468. https://doi.org/10.3390/en14020468

---

<!-- TOC depthFrom:1 depthTo:6 withLinks:1 updateOnSave:1 orderedList:1 -->
- [1. System requirements](#system-requirements)
- [2. Downloading SPAMDA](#downloading-spamda)
- [3. Running SPAMDA](#running-spamda)
- [4. SPAMDA Manual](#spamda-manual) 
- [5. License](#license)
- [6. Cite SPAMDA](#cite)
<!-- /TOC -->

# System requirements
SPAMDA has been developed in Java, therefore it is a multi-platform software tool. In this way, any computer having Java Virtual Machine (JVM) installed would be able to run SPAMDA.

SPAMDA requires Java JRE 1.8v or higher, accordingly Java version 8 needs to be installed in the system.

# Downloading SPAMDA
To download SPAMDA follow the next steps:

1. Download the repository https://github.com/ayrna/spamda.git on the computer.

After that, the following main folders and files appear on the computer:

    |-> dist: Contains the binary distribution of SPAMDA.
      |--> DB: Contains all the information managed by SPAMDA.
        |---> buoysDatabase: Contains the database of the buoys.
        |---> finalDatasets: Default folder to save the final datasets.
        |---> id1: Contains the information of the buoy entered as example.
        |---> reanalysisDatabase: Contains the database of the reanalysis data.
        |---> reanalysisFiles: Contains the reanalysis files entered through SPAMDA.
      |--> help: Contains the user manual of SPAMDA.
        |---> javadoc: Contains the Java documentacion.
        |---> userManual.pdf: The user manual.
      |--> lib: Contains the libraries used by SPAMDA.
      |--> README.TXT: Build output description generated by NetBeans IDE.
      |--> SPAMDA.jar: The runnable file containing SPAMDA.
    |-> lib: Contains the external libraries used by SPAMDA.
    |-> nbproject: Contains the configuration of the project of NetBeans IDE.
    |-> src: Contains the source code of SPAMDA.
    |-> COPYING: This file contains a copy of the license of the GNU GENERAL PUBLIC LICENSE.
    |-> LICENSE: This file contains a copy of the license of SPAMDA.
    |-> NetCDF-LICENSE: This file contains a copy of the license of the Library NetCDF Java version 4.6.10
    |-> README: This file contains the instructions for getting started with SPAMDA.
    |-> SLF4j-LICENSE: This file contains a copy of the license of the Library SLF4J version 1.7.25
    |-> WEKA-LICENSE: This file contains a copy of the license of the Library WEKA version 3.8.1


# Running SPAMDA
Follow the instructions indicated below to run SPAMDA depending on your platform.

## Linux
Open the *dist* folder and type the following command on the command-line of the terminal:
				
    java -jar SPAMDA.jar

## Windows
Open the *dist* folder and double-click on the SPAMDA.jar file.

## macOS
Open the *dist* folder and double-click on the SPAMDA.jar file.

# SPAMDA Manual
The user manual can be consulted in *dist/help/userManual.pdf* file.

# License
SPAMDA is distributed under the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. Please read the files *COPYING* and *LICENSE*.

# Cite
If you use SPAMDA, please cite the following work:

Gómez-Orellana, A.M.; Fernández, J.C.; Dorado-Moreno, M.; Gutiérrez, P.A.; Hervás-Martínez, C. Building Suitable Datasets for Soft Computing and Machine Learning Techniques from Meteorological Data Integration: A Case Study for Predicting Significant Wave Height and Energy Flux. Energies 2021, 14, 468. https://doi.org/10.3390/en14020468
