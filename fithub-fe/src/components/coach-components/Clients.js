import React, { useState, useEffect } from "react";
import LoadingSpinner from "../LoadingSpinner";
import "../../css/Clients.css";
import { jsPDF } from "jspdf";
import "jspdf-autotable";
import ConfirmDeleteModal from "../modals/ConfirmDeleteModal"; 
import ClientsMealPlanModal from "../modals/ClientsMealPlanModal"; 
import ClientsMembershipModal from "../modals/ClientsMembershipModal"; 
import SuccessModal from "../modals/SuccessModal"; 

const Clients = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [clientsData, setClientsData] = useState([
    {
      id: 1,
      userId: "nikolina-kokor",
      coachId: 3,
      firstName: "Nikolina",
      lastName: "Kokor"
    },
    {
      id: 2,
      userId: "matija-kokor",
      coachId: 3,
      firstName: "Matija",
      lastName: "Kokor"
    },
    {
      id: 3,
      userId: "nedzla-helac",
      coachId: 3,
      firstName: "Nedzla",
      lastName: "Helac"
    },
    {
      id: 4,
      userId: "mirza-bes",
      coachId: 3,
      firstName: "Mirza",
      lastName: "Besirevic"
    }
  ]);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [showMealPlanModal, setShowMealPlanModal] = useState(false); // State for meal plan modal
  const [clientToDelete, setClientToDelete] = useState(null);
  const [clientForMealPlan, setClientForMealPlan] = useState(null); // State for selected client for meal plan
  const [clientForMembership, setClientForMembership] = useState(null); // State for selected client for membership
  const [showMembershipModal, setShowMembershipModal] = useState(false);
  const clientsStorageKey = 'clientsData';

  useEffect(() => {
    setIsLoading(false);
  }, []);

  useEffect(() => {
    localStorage.setItem(clientsStorageKey, JSON.stringify(clientsData));
  }, [clientsData]);

  const downloadClientList = () => {
    const doc = new jsPDF();
  
    // Set Times New Roman font
    doc.setFont("times");
  
    // Set the title
    doc.setFontSize(16);
    doc.text("List of Clients", 105, 10, { align: "center" });
  
    // Prepare data for the table
    const data = clientsData.map(client => [client.firstName, client.lastName]);
  
    // Generate table
    doc.autoTable({
      startY: 20,
      head: [['First Name', 'Last Name']],
      body: data
    });
  
    // Download PDF
    doc.save('YourClients_List.pdf');
  };
  

  const handleDeleteIconClick = (id) => {
    setClientToDelete(id);
    setShowDeleteModal(true);
  };

  const confirmDeleteClient = () => {
    setClientsData(prevClients => prevClients.filter(client => client.id !== clientToDelete));
    setShowDeleteModal(false);
    setShowSuccessModal(true);
    setClientToDelete(null);
  };

  const cancelDeleteClient = () => {
    setShowDeleteModal(false);
    setClientToDelete(null);
  };

  const closeSuccessModal = () => {
    setShowSuccessModal(false);
  };

  const handleMealPlanClick = (client) => {
    setClientForMealPlan(client);
    setShowMealPlanModal(true);
  };
  const handleMembershipClick = (client) => {
    setClientForMembership(client);
    setShowMembershipModal(true);
  };
  const closeMembershipModal = () => {
    setShowMembershipModal(false);
    setClientForMembership(null);
  };


  const closeMealPlanModal = () => {
    setShowMealPlanModal(false);
    setClientForMealPlan(null);
  };

  const renderClients = (
    <div id="clients-coach-page-div" className="page-div">
      <h1 id="clients-coach-page-title">YOUR CLIENTS</h1> {/* Add title */}
      <table>
        <thead>
          <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th></th>
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {clientsData.map(client => (
            <tr key={client.id}>
              <td>{client.firstName}</td>
              <td>{client.lastName}</td>
              <td className="no-border membership-button"><button onClick={() => handleMembershipClick(client)}>Membership</button></td>
              <td className="no-border mealplan-button"><button onClick={() => handleMealPlanClick(client)}>Meal Plan</button></td>
              <td className="no-border">
                <button className="delete-button" onClick={() => handleDeleteIconClick(client.id)}>
                  <img 
                    src="/images/delete-icon.png"
                    alt="Delete"
                    className="delete-icon"
                  />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <button className="download-button" onClick={downloadClientList}>Download Client List</button>
    </div>
  );

  return (
    <div className="App">
      {isLoading ? <LoadingSpinner /> : renderClients}
      {showDeleteModal && (
        <ConfirmDeleteModal
          onCancel={cancelDeleteClient}
          onConfirm={confirmDeleteClient}
        />
      )}
      {showSuccessModal && (
        <SuccessModal
          message="Client successfully deleted."
          onClose={closeSuccessModal}
        />
      )}
      {showMealPlanModal && (
        <ClientsMealPlanModal
          client={clientForMealPlan}
          onClose={closeMealPlanModal}
        />
      )}
      {showMembershipModal && (
        <ClientsMembershipModal
          client={clientForMembership}
          onClose={closeMembershipModal}
        />
      )}
    </div>
  );
}

export default Clients;