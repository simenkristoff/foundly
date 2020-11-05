module.exports = {
  preset: '@vue/cli-plugin-unit-jest',
  setupFiles: ["./tests/unit/setup-jest.js"],
  transform: {
		"^.+\\.vue$": "vue-jest",
		"^.+\\.js$": "babel-jest",
	}
}
