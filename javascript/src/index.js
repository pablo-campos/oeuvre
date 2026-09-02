/**
 * JavaScript Playground Entrypoint
 */
function main() {
  console.log("========================================");
  console.log(" Hello, World from JavaScript (Node.js)!");
  console.log("========================================");
  console.log(`Runtime: Node.js ${process.version}`);
  console.log(`Platform: ${process.platform} (${process.arch})`);
  console.log(`Timestamp: ${new Date().toISOString()}`);
  console.log("To install packages in this module, run: npm install <package-name>");
  console.log("----------------------------------------\n");
}

main();
