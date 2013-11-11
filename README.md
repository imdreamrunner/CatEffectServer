CatEffect CaMS Server
=====================

Description
-----------

The server software of CaMS is programmed in Java on top of Play Framework. It supports operating systems including Microsoft Windows, Linux, and Mac OS X.

The server software uses MySQL as the database management system.

Installation Guide
------------------

(1) Preparing Server and Operating System

Users should have at least one server machine running supported operating system.

(2) Installing Java Runtime Environment and Java Development Kit

The Java Runtime Environment (JRE) provides the libraries, the Java Virtual Machine, and other components to run applets and applications written in the Java programming language. In addition, two key deployment technologies are part of the JRE: Java Plug-in, which enables applets to run in popular browsers; and Java Web Start, which deploys standalone applications over a network.

The Java Development Kit (JDK) is an implementation of either one of the Java SE, Java EE or Java ME platforms released by Oracle Corporation in the form of a binary product aimed at Java developers on Solaris, Linux, Mac OS X or Windows. Since the introduction of the Java platform, it has been by far the most widely used Software Development Kit (SDK). On 17 November 2006, Sun announced that it would be released under the GNU General Public License (GPL), thus making it free software. This happened in large part on 8 May 2007, when Sun contributed the source code to the OpenJDK.

Both JRE and JDK are necessary to run the Play framework which is used by the server software.

It installation packages of both runtime environment and development kit can be downloaded from: http://www.oracle.com/technetwork/java/javase/downloads/index.html

(3) Installing Play Framework

Play is a high-productivity Java and Scala web application framework that integrates the components and APIs you need for modern web application development.

Play is based on a lightweight, stateless, web-friendly architecture and features predictable and minimal resource consumption (CPU, memory, threads) for highly-scalable applications thanks to its reactive model, based on Iteratee IO.
We are using Play version 2.1.5. The binary file of Play framework can be download from http://www.playframework.com/download.

When you download the zipped binary files, please unzip them into a directory. And you should add the address of that directory into your Environment Variables on Windows or bash_profile on Linux and Mac OS X.

Project Structure
-----------------

```
app                 → Application sources
 └ models           → Models in CaMS database
 └ btu_models       → Models in university database
 └ views            → Views typically in html files with scala
 └ controllers      → controllers
test                → test cases
```
