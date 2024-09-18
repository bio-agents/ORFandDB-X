#!/usr/bin/perl -W

use strict;

if(scalar(@ARGV)<2) {
	die "This program takes at least two parameters:\n\tone or more input files in mul format (and maybe compressed with gzip)\n\tthe output XML file\n";
}

local(*OUTPUT);

open(OUTPUT,'>'.$ARGV[$#ARGV]) || die "ERROR: Unable to open ".$ARGV[$#ARGV]." for writing\n";

# And now, let's work!!!
print OUTPUT <<EOF;
<?xml version='1.0' encoding='ISO-8859-1'?>

<!--
	This XML document was generated by Mul2ODB.pl script
	which was created by Jos� Mar�a Fern�ndez Gonz�lez
	jmfernandez\@cnio.es CNIO (C) 2006
-->
<MSASet xmlns='http://www.cnio.es/scombio/jmfernandez/ORFandDB/4.0/MSA'>
EOF

my($dumpres)=undef;
foreach my $ifile (@ARGV[0..($#ARGV - 1)]) {
	if(length($ifile)>0 && index($ifile,'-')==0) {
		# It is a parameter
		if($ifile eq '-r') {
			$dumpres=1;
		} elsif($ifile eq '-R') {
			$dumpres=undef;
		} else {
			warn "ERROR: Unknown flag $ifile\n";
		}
		next;
	}

	local(*IFILE);
	
	my($openline)=undef;
	if($ifile =~ /\.gz$/) {
		$openline='gunzip -c "'.$ifile.'" |';
	} else {
		$openline='<'.$ifile;
	}
	
	if(open(IFILE,$openline)) {
		my($line);
		my(%mul)=();
		my(@mulord)=();
		my($alignlength)=undef;
		while($line=<IFILE>) {
			chomp($line);
			next if($line eq '');
			
			if($line eq '//') {
				if(scalar(@mulord)>0) {
					# We have to write the MSA
					print OUTPUT "\t<MSA length='$alignlength'>\n";
					my($id)=1;
					my($alength)='a' x $alignlength;
					foreach my $msa (@mulord) {
						print OUTPUT "\t\t<gappedFragment name='".$msa->[0]."'",
							(defined($msa->[2])?" start='$msa->[2]'":''),
							(defined($msa->[3])?" end='$msa->[3]'":''),
							"><content type='res' id='$id'>$msa->[1]</content>";
						
						if(defined($dumpres)) {
							print OUTPUT "<residues>";
							my($buffer)='';
							my($pos)=$msa->[4];
							my($i)=0;
							foreach my $res (unpack($alength, $msa->[1])) {
								$i++;
								next  if($res eq '-' || $res eq '.');
								$buffer .= '<r n="'.$i.'" p="'.$pos.'">'.$res.'</r>';
								$pos++;
							}

							print OUTPUT $buffer,"</residues>";
						}
						print OUTPUT "</gappedFragment>\n";
						$id++;
					}
					
					print OUTPUT "\t</MSA>\n";
				}
				
				# And we have to clean up the memory structures
				@mulord=();
				%mul=();
				$alignlength=undef;
			} elsif(substr($line,0,1) eq '#') {
				# A comment line?
			} else {
				# A multiple sequence alignment line?
				my($id,$align)=split(/[ \t]+/,$line,2);
				# Removing white spaces from the alignment
				$align =~ tr/ //d  if(defined($align));
				unless(defined($align) && $id ne '' && !exists($mul{$id})) {
					warn "ERROR: Incorrect file format: incorrect alignment line\n";
					last;
				}
				
				$alignlength=length($align)  unless(defined($alignlength));
				if($alignlength!=length($align)) {
					warn "ERROR: Incorrect file format: alignment line $id should have $alignlength residues/gaps instead of ",length($align),"\n";
					last;
				}
				
				# Do we have extra info?
				my($bareid,$begin,$end)=(undef,undef,undef);
				if($id =~ /^([^\/]+)\/([1-9][0-9]*)-([1-9][0-9]*)$/) {
					$begin=$2;
					$end=$3;
					$bareid=$1;
				} else {
					$bareid=$id;
				}
				
				push(@mulord,[$bareid,$align,$begin,$end,(defined($begin)?$begin:1)]);
				$mul{$id}=$#mulord;
			}
		}
		close(IFILE);
	} else {
		warn "ERROR: Unable to open $ifile for reading\n";
	}
}

print OUTPUT "</MSASet>\n";

# We have to close the output file once we have finished!
close(OUTPUT);
