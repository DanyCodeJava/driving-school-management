const template = document.createElement('template');

template.innerHTML = `
<style>
	@import url('https://netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css');
</style>
<div  class="panel-body inf-content">
<div class="row">
  <div class="col-md-12">
    <strong class="text-center">Information</strong><br>
    <div class="table-responsive">
      <table class="table table-user-information">
        <tbody>
        <tr>
          <td>
            <strong>
              <span class="glyphicon glyphicon-road text-primary"></span>
             Car Type
            </strong>
          </td>
          <td class="text-primary">
            <span class="text-center carType" ></span>
          </td>
        </tr>
        <tr>
          <td>
            <strong>
              <span class="glyphicon glyphicon-euro  text-primary"></span>
              Cost
            </strong>
          </td>
          <td class="text-primary">
            <span class="text-center cost"></span>
          </td>
        </tr>
        <tr>
          <td>
            <strong>
              <span class="glyphicon glyphicon-user text-primary"></span>
              Instructor name
            </strong>
          </td>
          <td class="text-primary">
            <span class="text-center instructorName" ></span>
          </td>
        </tr>
        <tr>
          <td>
            <strong>
              <span class="glyphicon glyphicon-time text-primary"></span>
              Training duration
            </strong>
          </td>
          <td class="text-primary">
            <span class="text-center duration"></span>
          </td>
        </tr>
        <tr>
          <td>
            <strong>
              <span class="glyphicon glyphicon-stats text-primary"></span>
              Status
            </strong>
          </td>
          <td class="text-primary status">
            <span class="text-center"></span>
          </td>
        </tbody>
      </table>
    </div>
  </div>
</div>
<div class="text-center">
  <a  href="#"  class="btn btn-primary text-center margin-button change-status-completed">Complete</a>
</div>
</div>`
export class StudentTrainingPackageDetailsView extends HTMLElement{
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
        this._shadowRoot.querySelector('.carType').innerHTML = this.carType;
        this._shadowRoot.querySelector('.cost').innerHTML = this.cost;
        this._shadowRoot.querySelector('.instructorName').innerHTML = this.instructorName;
        this._shadowRoot.querySelector('.duration').innerHTML = this.duration;
        this._shadowRoot.querySelector('.status').innerHTML = this.status;
        const completeBtn  = this._shadowRoot.querySelector('.change-status-completed');
        completeBtn.addEventListener('click',this.onCompleteButtonClick.bind(this));
        if(this.status === "COMPLETED"){
            completeBtn.remove();
        }
      }
      
      onCompleteButtonClick(){
        this.submitChangeStatusCompleted().then(this.onMarkedAsCompleted.bind(this));
      }
      onMarkedAsCompleted(){
        this._shadowRoot.querySelector('.status').innerHTML = "COMPLETED";
        const completeBtn  = this._shadowRoot.querySelector('.change-status-completed');        
            completeBtn.remove();        
      }
      async submitChangeStatusCompleted(){
        const headers = { "Content-Type": "application/json"};
        headers[this.csrfHeaderName] = this.csrfValue;
        const response = await fetch(`/students/${this.studentId}/training-packages/${this.studentTrainingPackageId}`,{
            method: "PATCH",
            headers,
            body:JSON.stringify({status:"COMPLETED"})
            });
            if(!response.ok){
                throw new Error("Failed to patch student training package");
            }
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
      get carType() {
        return this.getAttribute('carType');
      }
      get studentTrainingPackageId() {
        return this.getAttribute('studentTrainingPackageId');
      }
      get cost() {
        return this.getAttribute('cost');
      }
      get duration() {
        return this.getAttribute('duration');
      }
      get instructorName() {
        return this.getAttribute('instructorName');
      }
      get status() {
        return this.getAttribute('status');
      }

}

