const chalk = require('chalk');
const semver = require('semver');
const BaseGenerator = require('generator-jhipster/generators/generator-base');
const jhipsterConstants = require('generator-jhipster/generators/generator-constants');
const packagejs = require('../../package.json');

const SERVER_TEST_SRC_DIR = jhipsterConstants.SERVER_TEST_SRC_DIR;

module.exports = class extends BaseGenerator {
    get initializing() {
        return {
            init(args) {
                if (args === 'default') {
                    // do something when argument is 'default'
                    this.suffix = 'App';
                }
            },
            readConfig() {
                this.jhipsterAppConfig = this.getAllJhipsterConfig();
                if (!this.jhipsterAppConfig) {
                    this.error('Cannot read .yo-rc.json');
                }
            },
            displayLogo() {
                // it's here to show that you can use functions from generator-jhipster
                // this function is in: generator-jhipster/generators/generator-base.js
                this.printJHipsterLogo();

                // Have Yeoman greet the user.
                this.log(
                    `\nWelcome to the ${chalk.bold.yellow('JHipster safe-extensions')} generator! ${chalk.yellow(
                        `v${packagejs.version}\n`
                    )}`
                );
            },
            checkJhipster() {
                const currentJhipsterVersion = this.jhipsterAppConfig.jhipsterVersion;
                const minimumJhipsterVersion = packagejs.dependencies['generator-jhipster'];
                if (!semver.satisfies(currentJhipsterVersion, minimumJhipsterVersion)) {
                    this.warning(
                        `\nYour generated project used an old JHipster version (${currentJhipsterVersion})... you need at least (${minimumJhipsterVersion})\n`
                    );
                }
            }
        };
    }

    prompting() {
        const prompts = [
            {
                when: () => typeof this.entityName === 'undefined',
                type: 'input',
                name: 'entityName',
                message: 'What entity would you like to extend?',
                default: 'Hello'
            },
            {
                when: () => typeof this.suffix === 'undefined',
                type: 'input',
                name: 'suffix',
                message: 'What suffix would you like to add to generated classes?',
                default: 'App'
            }
        ];

        const done = this.async();
        this.prompt(prompts).then(answers => {
            this.promptAnswers = answers;
            // To access props answers use this.promptAnswers.someOption;
            done();
        });
    }

    writing() {
        // read config from .yo-rc.json
        this.baseName = this.jhipsterAppConfig.baseName;
        this.packageName = this.jhipsterAppConfig.packageName;
        this.packageFolder = this.jhipsterAppConfig.packageFolder;
        this.clientFramework = this.jhipsterAppConfig.clientFramework;
        this.clientPackageManager = this.jhipsterAppConfig.clientPackageManager;
        this.buildTool = this.jhipsterAppConfig.buildTool;

        // use function in generator-base.js from generator-jhipster
        this.angularAppName = this.getAngularAppName();

        // use constants from generator-constants.js
        const javaDir = `${jhipsterConstants.SERVER_MAIN_SRC_DIR + this.packageFolder}/`;

        // variable from questions
        if (typeof this.suffix === 'undefined') {
            this.suffix = this.promptAnswers.suffix;
        }

        if (typeof this.entityName === 'undefined') {
            this.entityName = this.promptAnswers.entityName;
        }

        this.subPackageName = this.suffix.toLowerCase();
        this.entityVariableName = this.entityName.toLowerCase();

        // show all variables
        this.log('\n--- Generating ---');
        this.log(`service: ${javaDir}service/${this.subPackageName}/${this.entityName}Service${this.suffix}.java`);
        this.log(`resource:${javaDir}web/rest/${this.subPackageName}Resource${this.suffix}.java`);
        this.log('------\n');

        this.template('ExtendedService.java', `${javaDir}service/${this.subPackageName}/${this.entityName}Service${this.suffix}.java`);
        this.template('ExtendedResource.java', `${javaDir}web/rest/${this.subPackageName}/${this.entityName}Resource${this.suffix}.java`);
    }

    install() {
        const logMsg = `To install your dependencies manually, run: ${chalk.yellow.bold(`${this.clientPackageManager} install`)}`;

        const injectDependenciesAndConstants = err => {
            if (err) {
                this.warning('Install of dependencies failed!');
                this.log(logMsg);
            }
        };
        const installConfig = {
            bower: false,
            npm: this.clientPackageManager !== 'yarn',
            yarn: this.clientPackageManager === 'yarn',
            callback: injectDependenciesAndConstants
        };
        if (this.options['skip-install']) {
            this.log(logMsg);
        } else {
            this.installDependencies(installConfig);
        }
    }

    end() {
        this.log('End of safe-extensions generator');
    }
};
