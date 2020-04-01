/**
	 * @retireveAllAssets
	 * The method brings in all the assets found in the DB and passes the correct asset accordingly to each object based on the asset type.
	 * The method returns a list of Assets.
	 */
	public static List<Asset> retrieveAllAssets() {
		List<Asset> assets = new ArrayList<>();
		Connection conn = DBTool.connectToDB();
		String query = "select * from Asset;";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				/**
				 * The asset type is checked and then the appropriate information is passed into a Deposit, Stock or Private Investment obejct. 
				 */
				if (rs.getString("assetType").equals("D")) {
					Asset newDepo  = new DepositAsset(rs.getString("assetCode"), rs.getString("assetType"), rs.getString("label"), rs.getDouble("apr"));
					assets.add(newDepo);

				} else if (rs.getString("assetType").equals("S")) {
					Asset newStock = new Stock(rs.getString("assetCode"), rs.getString("assetType"), rs.getString("label"), rs.getDouble("quartDivi"), rs.getDouble("baseROR"),
							rs.getDouble("beta"), rs.getString("stockSymb"), rs.getDouble("SharePrice"));
					assets.add(newStock);

					
				} else if (rs.getString("assetType").equals("P")) {
					Asset newPI = new PrivateInvest(rs.getString("assetCode"), rs.getString("assetType"), rs.getString("label"), rs.getDouble("quartDivi"), 
							rs.getDouble("baseROR"), rs.getDouble("omega"), rs.getDouble("investmentValue"));
					assets.add(newPI);

				}
 			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		
		DBTool.disconnectFromDB(conn, ps, rs);
		return assets;
	}
	
//====================
/*
	 * @retrieveAllPerson 
	 * The method returns a list of Persons. 
	 * brings all Person records from the DB. 
	 */
	public static List<Person> retrieveAllPerson() {
		Connection conn = DBTool.connectToDB();
		String query = "select * from Person;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Person> persons = new ArrayList<>();
		
			try {
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				while (rs.next()) {
					/**
					 * While iterating through the loop, @retrieveAddress and @retrieveEmailAddress methods are used to bring in the 
					 * appropriate data and pass them into the Person object.
					 * @retrieveAddress is passed the address Id and returns an Address object.
					 * @retrieveEmailAddress is passed the person Id and returns the email address(s). 
					 */
					Name n = new Name(rs.getString("firstName"), rs.getString("lastName"));
					Person p = new Person(rs.getString("alphaCode"), rs.getString("brokerStat"), n, retrieveAddress(rs.getInt("addressId")), 
							retrieveEmailAddress(rs.getInt("personId")));
					persons.add(p);
				}
			} catch (SQLException sqle) {
				throw new RuntimeException(sqle);
			}
			
			DBTool.disconnectFromDB(conn, ps, rs);
			return persons;
	}
