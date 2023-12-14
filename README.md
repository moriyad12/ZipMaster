# ZipMaster

A versatile Java tool for file compression and extraction.

## Overview

ZipMaster is a Java application that allows you to easily compress and extract files of any type. It offers a simple and efficient way to reduce file size for storage, transfer, or backup purposes.

## Features

    Supports all common file formats: ZipMaster can handle any type of file you throw at it, from code and data to images and documents.
    Powerful compression algorithms: ZipMaster utilizes robust compression algorithms to achieve significant file size reduction.
    Flexible extraction options: Extract  file quickly and easily.

## Getting Started

    Clone the repository: git clone https://github.com/moriyad12/ZipMaster.git
    Build the project: Follow the instructions in the build.md file.
    Run the application: Execute the appropriate script or launch the Java class based on your build method.

## Usage

ZipMaster provides two main functionalities: compression and extraction.

Compression:

java -jar huffman_20011457.jar c absolute_path_to_input_file n
n is the number of bytes that will be considered together.
When dealing with larger input sizes (`n`), ZipMaster tends to produce smaller compressed file sizes, optimizing storage space. This is particularly beneficial when you need to conserve disk space and minimize storage costs.
However, it's essential to note that as the input size (`n`) increases, the time required for compression and extraction operations may also increase. This is a common characteristic of compression algorithms, where the trade-off for smaller file sizes is a longer processing time.

Extraction:

java -jar huffman_20011457.jar d absolute_path_to_input_file

We hope this README file helps you get started with ZipMaster!
