const template = document.createElement('template');

template.innerHTML = `
<div class="panel-content mt-2">
<div class="table-responsive">
<table class="table table-user-information">
  <tbody>  
  <tr>
    <td>
      <strong>
        <span class="glyphicon glyphicon-user text-primary"></span>
        Instructor name
      </strong>
    </td>
    <td class="text-primary">
      <input name="instructorName" placeholder="Instructor name" class="form-control"  type="text">
    </td>
  </tr>
  <tr>
    <td>
      <strong>
        <span class="glyphicon glyphicon-user text-primary"></span>
        Training Package
      </strong>
    </td>
    <td class="text-primary">
        <select class="form-select" name="trainingPackage">
    
        </select>
    </td>
  </tr>
  </tbody>
</table>
    <div class="text-center">
     <button type="button" class="btn btn-warning" >&nbsp&nbspSUBMIT <span class="glyphicon glyphicon-send"></span>&nbsp&nbsp</button>
    </div>
</div>
</div>
`
export class StudentTrainingPackageCreationForm extends HTMLElement{
    /**
     * @type {ShadowRoot}
     * @private
     */
    _shadowRoot

    constructor() {
        super();
        this._shadowRoot = this.attachShadow({ 'mode': 'open' });
        this._shadowRoot.appendChild(template.content.cloneNode(true));
      }

      connectedCallback(){
       
        this.fetchTrainingPackages().then();
        const button = this._shadowRoot.querySelector('button');
        button.addEventListener('click',this.onButtonClick.bind(this));
      }
      onButtonClick(){
        const select = this._shadowRoot.querySelector('select[name=trainingPackage]');
        const input = this._shadowRoot.querySelector('input[name=instructorName]');
       this.submit({trainingPackageId:select.value , instructorName:input.value});
      }
      async submit({trainingPackageId, instructorName}){
        const headers = { "Content-Type": "application/json"};
        headers[this.csrfHeaderName] = this.csrfValue;
        const response = await fetch(`/students/${this.studentId}/training-package`,{
            method: "POST",
            headers,
            body:JSON.stringify({trainingPackageId,instructorName, _csrf:this.csrfValue})
            })
      }
      get studentId() {
        return this.getAttribute('student');
      }
      get csrfValue() {
        return this.getAttribute('csrfValue');
      }
      get csrfHeaderName() {
        return this.getAttribute('csrfHeader');
      }
      async  fetchTrainingPackages() {
        const response = await fetch("/training-packages.json");
        const trainingPackages = await response.json();
        const select = this._shadowRoot.querySelector('select[name=trainingPackage]');
        select.innerHTML = trainingPackages.items
            .map(({carType, duration, cost, id})=> `<option value="${id}">${carType},${duration},${cost}</option>`)
            .join();
      }
      
}
