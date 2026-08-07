function SearchBar({ searchTerm, setSearchTerm }) {

  return (

    <div className="search-section">


      <input

        type="text"

        placeholder="Search job title, company, or location..."

        value={searchTerm}

        onChange={(e)=>setSearchTerm(e.target.value)}

      />


      <button>

        Search

      </button>


    </div>

  );

}


export default SearchBar;