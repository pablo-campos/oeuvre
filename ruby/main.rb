#!/usr/bin/env ruby
# frozen_string_literal: true

require "time"

def main
  puts "========================================"
  puts " Hello, World from Ruby!"
  puts "========================================"
  puts "Runtime: Ruby #{RUBY_VERSION} (#{RUBY_RELEASE_DATE} patchlevel #{RUBY_PATCHLEVEL})"
  puts "Platform: #{RUBY_PLATFORM}"
  puts "Timestamp: #{Time.now.iso8601}"
  puts "To install gems in this module, run: bundle install"
  puts "----------------------------------------\n"
end

main if __FILE__ == $PROGRAM_NAME
