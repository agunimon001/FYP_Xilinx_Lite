# Define procedures before running main code last

# == Procedures ==

proc open_project {} {
	global projectName
	if [file exists $projectName.xise] {
		project open $projectName
		return true
	} else {
		puts "Error: Project Missing"
		return false
	}
}

proc new_project {} {
	global projectName
	if [file exists $projectName.xise] {
		puts "Error: project $projectName exists"
	} else {
		project new $projectName
	}
}

proc clean {} {
	if [open_project] {
		project clean
	}
}

proc set_attributes {} {
	# Assumes that remaining arguments are in order
	if [open_project] {
		project set family [lindex $argv 2]
		project set device [lindex $argv 3]
		project set package [lindex $argv 4]
		project set speed [lindex $argv 5]
		project set top-level-source-type [lindex $argv 6]
		project set synthesis_tool [lindex $argv 7]
		project set simulator [lindex $argv 8]
		project set preferred-language [lindex $argv 9]
		project set enable-message-filtering [lindex $argv 10]
		project save
		get_attributes
	}
}

proc get_attributes {} {
	if [open_project] {
		puts "Device Family            : [project get family]"
		puts "Device                   : [project get device]"
		puts "Package                  : [project get package]"
		puts "Speed Grade              : [project get speed]"
		puts "Top-Level Source Type    : [project get top-level-source-type]"
		puts "Synthesis Tool           : [project get synthesis]"
		puts "Simulator                : [project get simulator]"
		puts "Preferred Language       : [project get preferred-language]"
		puts "Enable Message Filtering : [project get enable-message-filtering]"
	}
}

# String manipulation required in calling program
proc get_family_list {} {
	puts [exec partgen -h]
}

proc get_arch_data {arch} {
	puts [exec partgen -arch $arch]
}

proc add_file {fileVar} {
	if [open_project] {
		xfile add $fileVar
		project save
		puts [search $fileVar]
	}
}

# view all verilog files in project
proc view_files {} {
	if [open_project] {
		puts [search *.v]
	}
}

# Currently only implements for one synthesis_tool
proc synthesize {} {
	if [open_project] {
		puts [process run "Synthesize - XST" -force rerun_all]
	}
}

# == Main Code ==

if {[llength $argv] < 2} {
	puts "Error: arguments insufficient '$argc'"
	return false
}

set projectName [lindex $argv 0]
set option [lindex $argv 1]

switch $option {
	"open_project" {open_project}
	"synthesize" {synthesize}
	"clean" {clean}
	"new_project" {new_project}
	"get_attributes" {get_attributes}
	"get_family_list" {get_family_list}
	"get_arch_data" {get_arch_data [lindex $argv 2]}
	"set_attributes" {set_attributes}
	"add_file" {add_file [lindex $argv 2]}
	"view_files" {view_files}
	default {puts "ERROR: option invalid"}
}