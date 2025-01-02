#!/bin/bash

# FTP server details
FTP_HOST="ftp.etilize.com"
FTP_USER="thalerusodup"
FTP_PASS='7fHEG!pT8L5GB7tf'
REMOTE_DIR="MasterFederationCatalog"
LOCAL_DIR="/root/odf-transmutor/input-xml-dir"

# Get today's date in YYYYMMDD format
current_date=$(date +%Y%m%d)

# Function to download file
download_file() {
    local file_name=$1
    echo "Downloading $file_name..."
    ftp -inv $FTP_HOST << EOF
user $FTP_USER $FTP_PASS
cd $REMOTE_DIR
get $file_name $LOCAL_DIR/$file_name
bye
EOF
}

# Function to unzip the downloaded file
unzip_file() {
    local zip_file=$1
    echo "Unzipping $zip_file..."
    unzip -o "$zip_file" -d "$LOCAL_DIR"
}

# Function to rename XML file to xml
rename_file() {
    local xml_file=$1
    local new_file="${xml_file%.XML}.xml"
    echo "Renaming $xml_file to $new_file..."
    mv "$xml_file" "$new_file"
}

# Attempt to download the file for today and up to three days prior
for i in {0..3}; do
    # Format the date for the filename
    check_date=$(date -d "$current_date - $i days" +%Y%m%d)
    file_name="BSDCatalog_${check_date}.XML.zip"

    # Check if the file exists on the FTP server
    echo "Checking for $file_name..."
    file_exists=$(ftp -inv $FTP_HOST << EOF | grep -c "$file_name"
user $FTP_USER $FTP_PASS
cd $REMOTE_DIR
ls
bye
EOF
)

    if [ "$file_exists" -gt 0 ]; then
        download_file "$file_name"

        # Unzip the downloaded file
        unzip_file "$LOCAL_DIR/$file_name"

        # Rename the XML file from uppercase to lowercase
        xml_file="${LOCAL_DIR}/BSDCatalog_${check_date}.XML"  # Adjust this if your XML filename pattern changes.
        rename_file "$xml_file"
	
        # Remove the last extension (.zip)
	file_name_without_zip="${file_name%.*}"

	# Remove the second last extension (.XML)
	file_name_without_extensions="${file_name_without_zip%.*}"

	echo "Filename without extensions: $file_name_without_extensions to be fed into ODF transmutor endpoint..."
	# Hit the ODF transmutor endpoint with curl using POST:
	curl -i -X POST -H "Content-Type:application/json" "http://192.168.10.86:8080/viewsonic-batch-job/execute?username=test&file-name=$file_name_without_extensions"

        exit 0  # Exit after successful download and processing
    fi
done

echo "No files found for the specified dates."
