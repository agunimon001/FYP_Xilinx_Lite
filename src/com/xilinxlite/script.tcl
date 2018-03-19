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
		project set "Consider Include Files in Search" true
		# default settings for new project
		project set family spartan
		project set package csg225
	}
}

proc clean {} {
	if [open_project] {
		project clean
	}
}

proc get_attributes {} {
	if [open_project] {
		get_family
		get_device
		get_package
		get_speed
		get_top_level_source_type
		get_synthesis
		get_simulator
		get_language
		get_message_filter
	}
}

proc get_family {} {
	if [open_project] {
		puts "Device Family            : [project get family]"
	}
}

proc get_device {} {
	if [open_project] {
		puts "Device                   : [project get device]"
	}
}

proc get_package {} {
	if [open_project] {
		puts "Package                  : [project get package]"
	}
}

proc get_speed {} {
	if [open_project] {
		puts "Speed Grade              : [project get speed]"
	}
}

proc get_top_level_source_type {} {
	if [open_project] {
		puts "Top-Level Source Type    : [project get top-level-source-type]"
	}
}

proc get_synthesis {} {
	if [open_project] {
		puts "Synthesis Tool           : [project get synthesis]"
	}
}

proc get_simulator {} {
	if [open_project] {
		puts "Simulator                : [project get simulator]"
	}
}

proc get_language {} {
	if [open_project] {
		puts "Preferred Language       : [project get preferred-language]"
	}
}

proc get_message_filter {} {
	if [open_project] {
		puts "Enable Message Filtering : [project get enable-message-filtering]"
	}
}

proc set_family {val} {
	if [open_project] {
		project set family $val
		project save
		get_family
	}
}

proc set_device {val} {
	if [open_project] {
		project set device $val
		project save
		get_device
	}
}

proc set_package {val} {
	if [open_project] {
		project set package $val
		project save
		get_package
	}
}

proc set_speed {val} {
	if [open_project] {
		project set speed $val
		project save
		get_speed
	}
}

proc set_top_level_source_type {val} {
	if [open_project] {
		project set top-level-source-type $val
		project save
		get_top_level_source_type
	}
}

proc set_synthesis {val} {
	if [open_project] {
		project set synthesis_tool $val
		project save
		get_synthesis
	}
}

proc set_simulator {val} {
	if [open_project] {
		project set simulator $val
		project save
		get_simulator
	}
}

proc set_language {val} {
	if [open_project] {
		project set preferred-language $val
		project save
		get_language
	}
}

proc set_message_filter {val} {
	if [open_project] {
		project set enable-message-filtering $val
		project save
		get_message_filter
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

proc remove_file {fileVar} {
	if [open_project] {
		xfile remove $fileVar
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
		clean
		puts [process run "Synthesize - XST" -force rerun_all]
	}
}

# Simulate
proc simulate {verilogTest file_list} {
	if [open_project] {
		project clean
		foreach file $file_list {
			puts [exec vlogcomp $file]
		}
		puts [exec fuse -intstyle ise -incremental -lib unisims_ver -lib unimacro_ver -lib xilinxcorelib_ver -lib secureip -o $verilogTest\_isim_beh work.$verilogTest work.glbl]
		# return simulation data by running the simulation file
	}
}

proc set_top_module {fileVar} {
	if [open_project] {
		project set top $fileVar
		get_top_module
	}
}

proc get_top_modules {} {
	if [open_project] {
		puts [search /*]
	}
}

proc get_top_module {} {
	if [open_project] {
		puts [project get top]
	}
}

proc generate_programming_file {} {
	if [open_project] {
		puts [process run "Generate Programming File" -force rerun_all]
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
	"add_file" {add_file [lindex $argv 2]}
	"remove_file" {remove_file [lindex $argv 2]}
	"view_files" {view_files}
	"set_family" {set_family [lindex $argv 2]}
	"get_family" {get_family}
	"set_device" {set_device [lindex $argv 2]}
	"get_device" {get_device}
	"set_package" {set_package [lindex $argv 2]}
	"get_package" {get_package}
	"set_speed" {set_speed [lindex $argv 2]}
	"get_speed" {get_speed}
	"set_top_level_source_type" {set_top_level_source_type [lindex $argv 2]}
	"get_top_level_source_type" {get_top_level_source_type}
	"set_synthesis" {set_synthesis [lindex $argv 2]}
	"get_synthesis" {get_synthesis}
	"set_simulator" {set_simulator [lindex $argv 2]}
	"get_simulator" {get_simulator}
	"set_language" {set_language [lindex $argv 2]}
	"get_language" {get_language}
	"set_message_filter" {set_message_filter [lindex $argv 2]}
	"get_message_filter" {get_message_filter}
	"set_top_module" {set_top_module [lindex $argv 2]}
	"get_top_module" {get_top_module}
	"get_top_modules" {get_top_modules}
	"simulate" {simulate [lindex $argv 2] [lrange $argv 3 end]}
	"generate_programming_file" {generate_programming_file}
	default {puts "Error: option invalid"}
}