import { UserEmailChecker } from "./checkEmail.js";
export class StudentFormChecker {
    /**
     * @type {HTMLFormElement}
     */
    form;
    /**
     * @type {string}
     */
    emailValidURL;
    /**
     * @type {string}
     */
    csrfValue;

    /**
     * @type {UserEmailChecker}
     */
    userEmailChecker;
    /**
     * @type {(messageType:string, message:string)=>void}
     */
    showModalDialog;



    /**
     * 
     * @param {HTMLFormElement} form 
     * @param {string} emailValidURL 
     * @param {string} csrfValue 
     * @param {(messageType:string, message:string)=>void} showModalDialog 
     */
    constructor({ form, emailValidURL, csrfValue, showModalDialog }) {
        this.form = form;
        this.emailValidURL = emailValidURL;
        this.csrfValue = csrfValue;
        this.showModalDialog = showModalDialog;
        this.userEmailChecker = new UserEmailChecker({
            onEmailOk: () => {
                if (!this.syncValidations()) {
                    return;
                }
                this.form.submit();

            },
            showModalDialog: this.showModalDialog,
            csrfValue: this.csrfValue,
            url: this.emailValidURL
        })
        this.form.addEventListener('submit', this.onFormSubmit.bind(this));
        this.form.querySelectorAll('input[type=password]').forEach(input => {
            input.addEventListener('change', () => this.validatePasswordMatch(true));
        });
    }
    /**
     * 
     * @param {SubmitEvent} event 
     */

    onFormSubmit(event) {
        if (!this.syncValidations()) {
            event.preventDefault();
            return;
        }
        const userId = this.form.querySelector('input[name=id]')?.value;
        const userEmail = this.form.querySelector('input[name=email]')?.value;
        this.userEmailChecker.execute({ event, userEmail, userId });
    }
    /**
     * 
     * @param {boolean} bypassEmpty 
     * @returns {boolean}
     */
    validatePasswordMatch(bypassEmpty) {
        const passwordInput = this.form.querySelector('#passwordInput');
        const confirmPasswordInput = this.form.querySelector('#confirmPasswordInput');
        if (bypassEmpty && (!passwordInput.value || !confirmPasswordInput.value)) {
            return true;
        }
        const alertPassword = this.form.querySelector('#alertPassword');
        if (passwordInput.value !== confirmPasswordInput.value) {
            alertPassword.classList.remove('d-none');
            return false;
        } else {
            alertPassword.classList.add('d-none');
            return true;
        }

    }
    syncValidations() {
        return this.validatePasswordMatch(false);
    }
}