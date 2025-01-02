#!/bin/bash

# FTP server details
FTP_SERVER="ftp.etilizepak.com"
FTP_USER="data"
FTP_PASSWORD="data"
FTP_FOLDER="ODF-Transmutor-Output"

# Base directory containing job folders
BASE_DIR="/root/odf-transmutor/output-csv-dir"

# Step 1: Identify the latest job folder
LATEST_FOLDER=$(ls -dt "$BASE_DIR"/jobInstanceId_*/ | head -n 1)

# Check if a folder was found
if [ -z "$LATEST_FOLDER" ]; then
	echo "No latest job folders found."
	exit 1
fi

# Extract the Job folder name (e.g., jobInstanceId_1) to be created on FTP Server
JOB_FOLDER_NAME=$(basename "$LATEST_FOLDER")
# Step 2: Upload the latest folder to FTP server
ftp -inv $FTP_SERVER <<EOF
user $FTP_USER $FTP_PASSWORD
binary
cd $FTP_FOLDER
mkdir $JOB_FOLDER_NAME
cd $JOB_FOLDER_NAME
lcd "$LATEST_FOLDER"
mput *
bye
EOF

echo "Successfully uploaded folder $(basename "$LATEST_FOLDER") to $FTP_SERVER"

