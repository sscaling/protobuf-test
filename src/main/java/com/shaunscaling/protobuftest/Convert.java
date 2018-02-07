package com.shaunscaling.protobuftest;

import com.shaunscaling.protobuftest.protos.AddressBookProtos;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.proto.ProtoParquetWriter;

import java.io.FileInputStream;
import java.io.IOException;

public class Convert {
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println("Convert protobuf file to parquet\n   Usage:  java com.shaunscaling.protobuftest.Convert <in> <out>");
            System.exit(-1);
        }

        String in = args[0];
        String out = args[1];

        // Read the existing address book.
        AddressBookProtos.AddressBook addressBook =
                AddressBookProtos.AddressBook.parseFrom(new FileInputStream(in));

        // Convert to parquet + Write Out
        CompressionCodecName compressionCodecName = CompressionCodecName.UNCOMPRESSED;

        // TODO: understand these parameters more (probably need to use existing values?)
        int blockSize = 256 * 1024 * 1024;
        int pageSize = 64 * 1024;

        Path outputPath = new Path(out);

        try (ProtoParquetWriter<AddressBookProtos.AddressBook> parquetWriter = new ProtoParquetWriter<AddressBookProtos.AddressBook>(outputPath,
                AddressBookProtos.AddressBook.class, compressionCodecName, blockSize, pageSize)) {

            parquetWriter.write(addressBook);
        }

        System.out.println("Done");
    }
}
