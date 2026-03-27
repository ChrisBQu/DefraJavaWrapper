package source.defra;

import org.json.JSONArray;
import org.json.JSONObject;

public class DefraTransaction {

    static {
		System.loadLibrary("nativewrapper");
        System.loadLibrary("defradb");
    }

    // Core Transaction Methods
    private native DefraResult TransactionCommitNative(long txnPtr);
    private native void TransactionDiscardNative(long txnPtr);
    
    // ACP Methods
    private native DefraResult ACPAddDACPolicyNative(long txnPtr, long identityPtr, String policy);
    private native DefraResult ACPAddDACActorRelationshipNative(long txnPtr, long identityPtr, String collection, String docID, String relation, String actor);
    private native DefraResult ACPDeleteDACActorRelationshipNative(long txnPtr, long identityPtr, String collection, String docID, String relation, String actor);
    private native DefraResult ACPDisableNACNative(long txnPtr, long identityPtr);
    private native DefraResult ACPReEnableNACNative(long txnPtr, long identityPtr);
    private native DefraResult ACPAddNACActorRelationshipNative(long txnPtr, long identityPtr, String relation, String actor);
    private native DefraResult ACPDeleteNACActorRelationshipNative(long txnPtr, long identityPtr, String relation, String actor);
    private native DefraResult ACPGetNACStatusNative(long txnPtr, long identityPtr);
    
    // Collection Methods
    private native DefraResult AddCollectionNative(long txnPtr, String sdl, long identityPtr);
    private native DefraResult DescribeCollectionNative(long txnPtr, DefraCollectionOptions options, long identityPtr);
    private native DefraResult PatchCollectionNative(long txnPtr, String patch, String lensConfig, long identityPtr);
    private native DefraResult SetActiveCollectionNative(long txnPtr, DefraCollectionOptions options, long identityPtr);
    private native DefraResult TruncateCollectionNative(long txnPtr, DefraCollectionOptions options, long identityPtr);
    
    // Document Methods
    private native DefraResult AddDocumentNative(long txnPtr, String json, int isEncrypted, String encryptedFields, DefraCollectionOptions options, long identityPtr);
    private native DefraResult DeleteDocumentNative(long txnPtr, String docID, String filter, DefraCollectionOptions options, long identityPtr);
    private native DefraResult GetDocumentNative(long txnPtr, String docID, boolean showDeleted, DefraCollectionOptions options, long identityPtr);
    private native DefraResult UpdateDocumentNative(long txnPtr, String docID, String filter, String updater, DefraCollectionOptions options, long identityPtr);
    
    // Encrypted Index Methods
    private native DefraResult NewEncryptedIndexNative(long txnPtr, String collectionName, String fieldName, long identityPtr);
    private native DefraResult ListEncryptedIndexesNative(long txnPtr, String collectionName, long identityPtr);
    private native DefraResult DeleteEncryptedIndexNative(long txnPtr, String collectionName, String fieldName, long identityPtr);
    
    // Index Methods
    private native DefraResult NewIndexNative(long txnPtr, String indexName, String fields, boolean isUnique, DefraCollectionOptions options, long identityPtr);
    private native DefraResult ListIndexesNative(long txnPtr, DefraCollectionOptions options, long identityPtr);
    private native DefraResult DeleteIndexNative(long txnPtr, String indexName, DefraCollectionOptions options, long identityPtr);
    
    // Identity Methods
    private native DefraResult GetNodeIdentityNative(long txnPtr);
    
    // Lens Methods
    private native DefraResult SetLensNative(long txnPtr, long identityPtr, String src, String dst, String cfg);
    private native DefraResult AddLensNative(long txnPtr, long identityPtr, String cfg);
    private native DefraResult ListLensesNative(long txnPtr, long identityPtr);
    
    // P2P Methods
    private native DefraResult GetP2PInfoNative(long txnPtr, long identityPtr);
    private native DefraResult ListP2PActivePeersNative(long txnPtr, long identityPtr);
    private native DefraResult ListP2PReplicatorsNative(long txnPtr, long identityPtr);
    private native DefraResult AddP2PReplicatorNative(long txnPtr, String collections, String addresses, long identityPtr);
    private native DefraResult DeleteP2PReplicatorNative(long txnPtr, String collections, String id, long identityPtr);
    private native DefraResult AddP2PCollectionNative(long txnPtr, String collections, long identityPtr);
    private native DefraResult DeleteP2PCollectionNative(long txnPtr, String collections, long identityPtr);
    private native DefraResult ListP2PCollectionsNative(long txnPtr, long identityPtr);
    private native DefraResult AddP2PDocumentNative(long txnPtr, String collections, long identityPtr);
    private native DefraResult DeleteP2PDocumentNative(long txnPtr, String collections, long identityPtr);
    private native DefraResult ListP2PDocumentsNative(long txnPtr, long identityPtr);
    private native DefraResult SyncP2PDocumentsNative(long txnPtr, String collection, String docIDs, String timeout, long identityPtr);
    private native DefraResult SyncP2PCollectionVersionsNative(long txnPtr, String versionIDs, String timeout, long identityPtr);
    private native DefraResult SyncP2PBranchableCollectionNative(long txnPtr, String collectionID, String timeout, long identityPtr);
    private native DefraResult ConnectP2PPeersNative(long txnPtr, String peerAddresses, long identityPtr);
    
    // Query Methods
    private native DefraResult ExecuteQueryNative(long txnPtr, String query, long identityPtr, String operationName, String variables);
    
    // View Methods
    private native DefraResult AddViewNative(long txnPtr, String query, String sdl, String transformCID, long identityPtr);
    private native DefraResult RefreshViewNative(long txnPtr, DefraCollectionOptions options, long identityPtr);
    
    // Block Verification
    private native DefraResult VerifyBlockSignatureNative(long txnPtr, String keyType, String publicKey, String cid, long identityPtr);
    
    private long txnPtr;

    public DefraTransaction(long ptr) {
        this.txnPtr = ptr;
    }

    public DefraResult commit() {
        return TransactionCommitNative(this.txnPtr);
    }

    public void discard() {
        TransactionDiscardNative(this.txnPtr);
    }

    // ACP Methods
    public DefraResult ACPAddDACPolicy(String policy) {
        return ACPAddDACPolicyNative(this.txnPtr, 0, policy);
    }

    public DefraResult ACPAddDACPolicy(String policy, DefraIdentity identity) {
        return ACPAddDACPolicyNative(this.txnPtr, identity.getPointer(), policy);
    }

    public DefraResult ACPAddDACActorRelationship(String collection, String docID, String relation, String actor) {
        return ACPAddDACActorRelationshipNative(this.txnPtr, 0, collection, docID, relation, actor);
    }

    public DefraResult ACPAddDACActorRelationship(String collection, String docID, String relation, String actor, DefraIdentity identity) {
        return ACPAddDACActorRelationshipNative(this.txnPtr, identity.getPointer(), collection, docID, relation, actor);
    }   

    public DefraResult ACPDeleteDACActorRelationship(String collection, String docID, String relation, String actor) {
        return ACPDeleteDACActorRelationshipNative(this.txnPtr, 0, collection, docID, relation, actor);
    }

    public DefraResult ACPDeleteDACActorRelationship(String collection, String docID, String relation, String actor, DefraIdentity identity) {
        return ACPDeleteDACActorRelationshipNative(this.txnPtr, identity.getPointer(), collection, docID, relation, actor);
    }

    public DefraResult ACPDisableNAC() {
        return ACPDisableNACNative(this.txnPtr, 0);
    }

    public DefraResult ACPDisableNAC(DefraIdentity identity) {
        return ACPDisableNACNative(this.txnPtr, identity.getPointer());
    }

    public DefraResult ACPReEnableNAC() {
        return ACPReEnableNACNative(this.txnPtr, 0);
    }

    public DefraResult ACPReEnableNAC(DefraIdentity identity) {
        return ACPReEnableNACNative(this.txnPtr, identity.getPointer());
    }

    public DefraResult ACPAddNACActorRelationship(String relation, String actor) {
        return ACPAddNACActorRelationshipNative(this.txnPtr, 0, relation, actor);
    }

    public DefraResult ACPAddNACActorRelationship(String relation, String actor, DefraIdentity identity) {
        return ACPAddNACActorRelationshipNative(this.txnPtr, identity.getPointer(), relation, actor);
    }

    public DefraResult ACPDeleteNACActorRelationship(String relation, String actor) {
        return ACPDeleteNACActorRelationshipNative(this.txnPtr, 0, relation, actor);
    }

    public DefraResult ACPDeleteNACActorRelationship(String relation, String actor, DefraIdentity identity) {
        return ACPDeleteNACActorRelationshipNative(this.txnPtr, identity.getPointer(), relation, actor);
    }

    public DefraResult ACPGetNACStatus() {
        return ACPGetNACStatusNative(this.txnPtr, 0);
    }

    public DefraResult ACPGetNACStatus(DefraIdentity identity) {
        return ACPGetNACStatusNative(this.txnPtr, identity.getPointer());
    }

    // Collection Methods
    public DefraResult addCollection(String sdl) {
        return AddCollectionNative(this.txnPtr, sdl, 0);
    }

    public DefraResult addCollection(String sdl, DefraIdentity identity) {
        return AddCollectionNative(this.txnPtr, sdl, identity.getPointer());
    }

    public DefraResult describeCollection(DefraCollectionOptions options) {
        return DescribeCollectionNative(this.txnPtr, options, 0);
    }

    public DefraResult describeCollection(DefraCollectionOptions options, DefraIdentity identity) {
        return DescribeCollectionNative(this.txnPtr, options, identity.getPointer());
    }

    public DefraResult patchCollection(String patch, String lensConfig) {
        return PatchCollectionNative(this.txnPtr, patch, lensConfig, 0);
    }

    public DefraResult patchCollection(String patch, String lensConfig, DefraIdentity identity) {
        return PatchCollectionNative(this.txnPtr, patch, lensConfig, identity.getPointer());
    }

    public DefraResult setActiveCollection(DefraCollectionOptions options) {
        return SetActiveCollectionNative(this.txnPtr, options, 0);
    }

    public DefraResult setActiveCollection(DefraCollectionOptions options, DefraIdentity identity) {
        return SetActiveCollectionNative(this.txnPtr, options, identity.getPointer());
    }

    public DefraResult truncateCollection(DefraCollectionOptions options) {
        return TruncateCollectionNative(this.txnPtr, options, 0);
    }

    public DefraResult truncateCollection(DefraCollectionOptions options, DefraIdentity identity) {
        return TruncateCollectionNative(this.txnPtr, options, identity.getPointer());
    }
	
	public DefraCollection getCollectionByName(String name) throws Exception {
		DefraCollectionOptions copts = new DefraCollectionOptions();
		copts.name = name;
		DefraResult describeResult = DescribeCollectionNative(this.txnPtr, copts, 0);
		if (describeResult.status != 0) {
			throw new Exception("Collection with the name " + name + " was not found.");
		}
		JSONArray array = new JSONArray(describeResult.value);
		JSONObject first = array.getJSONObject(0);
		String collectionID = first.getString("CollectionID");
		String versionID = first.getString("VersionID");
		return new DefraCollection(this.txnPtr, name, collectionID, versionID);		
	}
	
	public DefraCollection getCollectionByName(String name, DefraIdentity identity) throws Exception {
		DefraCollectionOptions copts = new DefraCollectionOptions();
		copts.name = name;
		DefraResult describeResult = DescribeCollectionNative(this.txnPtr, copts, identity.getPointer());
		if (describeResult.status != 0) {
			throw new Exception("Collection with the name " + name + " was not found.");
		}
		JSONArray array = new JSONArray(describeResult.value);
		JSONObject first = array.getJSONObject(0);
		String collectionID = first.getString("CollectionID");
		String versionID = first.getString("VersionID");
		return new DefraCollection(this.txnPtr, name, collectionID, versionID);		
	}

    // Document Methods
    public DefraResult addDocument(String json, boolean isEncrypted, String encryptedFields, DefraCollectionOptions options) {
        return AddDocumentNative(this.txnPtr, json, isEncrypted ? 1 : 0, encryptedFields, options, 0);
    }

    public DefraResult addDocument(String json, boolean isEncrypted, String encryptedFields, DefraCollectionOptions options, DefraIdentity identity) {
        return AddDocumentNative(this.txnPtr, json, isEncrypted ? 1 : 0, encryptedFields, options, identity.getPointer());
    }

    public DefraResult deleteDocument(String docID, String filter, DefraCollectionOptions options) {
        return DeleteDocumentNative(this.txnPtr, docID, filter, options, 0);
    }

    public DefraResult deleteDocument(String docID, String filter, DefraCollectionOptions options, DefraIdentity identity) {
        return DeleteDocumentNative(this.txnPtr, docID, filter, options, identity.getPointer());
    }

    public DefraResult getDocument(String docID, boolean showDeleted, DefraCollectionOptions options) {
        return GetDocumentNative(this.txnPtr, docID, showDeleted, options, 0);
    }

    public DefraResult getDocument(String docID, boolean showDeleted, DefraCollectionOptions options, DefraIdentity identity) {
        return GetDocumentNative(this.txnPtr, docID, showDeleted, options, identity.getPointer());
    }

    public DefraResult updateDocument(String docID, String filter, String updater, DefraCollectionOptions options) {
        return UpdateDocumentNative(this.txnPtr, docID, filter, updater, options, 0);
    }

    public DefraResult updateDocument(String docID, String filter, String updater, DefraCollectionOptions options, DefraIdentity identity) {
        return UpdateDocumentNative(this.txnPtr, docID, filter, updater, options, identity.getPointer());
    }

    // Encrypted Index Methods
    public DefraResult newEncryptedIndex(String collectionName, String fieldName) {
        return NewEncryptedIndexNative(this.txnPtr, collectionName, fieldName, 0);
    }

    public DefraResult newEncryptedIndex(String collectionName, String fieldName, DefraIdentity identity) {
        return NewEncryptedIndexNative(this.txnPtr, collectionName, fieldName, identity.getPointer());
    }

    public DefraResult listEncryptedIndexes(String collectionName) {
        return ListEncryptedIndexesNative(this.txnPtr, collectionName, 0);
    }

    public DefraResult listEncryptedIndexes(String collectionName, DefraIdentity identity) {
        return ListEncryptedIndexesNative(this.txnPtr, collectionName, identity.getPointer());
    }

    public DefraResult deleteEncryptedIndex(String collectionName, String fieldName) {
        return DeleteEncryptedIndexNative(this.txnPtr, collectionName, fieldName, 0);
    }

    public DefraResult deleteEncryptedIndex(String collectionName, String fieldName, DefraIdentity identity) {
        return DeleteEncryptedIndexNative(this.txnPtr, collectionName, fieldName, identity.getPointer());
    }

    // Index Methods
    public DefraResult newIndex(String indexName, String fields, boolean isUnique, DefraCollectionOptions options) {
        return NewIndexNative(this.txnPtr, indexName, fields, isUnique, options, 0);
    }

    public DefraResult newIndex(String indexName, String fields, boolean isUnique, DefraCollectionOptions options, DefraIdentity identity) {
        return NewIndexNative(this.txnPtr, indexName, fields, isUnique, options, identity.getPointer());
    }

    public DefraResult listIndexes(DefraCollectionOptions options) {
        return ListIndexesNative(this.txnPtr, options, 0);
    }

    public DefraResult listIndexes(DefraCollectionOptions options, DefraIdentity identity) {
        return ListIndexesNative(this.txnPtr, options, identity.getPointer());
    }

    public DefraResult deleteIndex(String indexName, DefraCollectionOptions options) {
        return DeleteIndexNative(this.txnPtr, indexName, options, 0);
    }

    public DefraResult deleteIndex(String indexName, DefraCollectionOptions options, DefraIdentity identity) {
        return DeleteIndexNative(this.txnPtr, indexName, options, identity.getPointer());
    }

    // Identity Methods
    public DefraResult getNodeIdentity() {
        return GetNodeIdentityNative(this.txnPtr);
    }

    // Lens Methods
    public DefraResult setLens(String src, String dst, String cfg) {
        return SetLensNative(this.txnPtr, 0, src, dst, cfg);
    }

    public DefraResult setLens(String src, String dst, String cfg, DefraIdentity identity) {
        return SetLensNative(this.txnPtr, identity.getPointer(), src, dst, cfg);
    }

    public DefraResult addLens(String cfg) {
        return AddLensNative(this.txnPtr, 0, cfg);
    }

    public DefraResult addLens(String cfg, DefraIdentity identity) {
        return AddLensNative(this.txnPtr, identity.getPointer(), cfg);
    }

    public DefraResult listLenses() {
        return ListLensesNative(this.txnPtr, 0);
    }

    public DefraResult listLenses(DefraIdentity identity) {
        return ListLensesNative(this.txnPtr, identity.getPointer());
    }

    // P2P Methods
    public DefraResult getP2PInfo() {
        return GetP2PInfoNative(this.txnPtr, 0);
    }

    public DefraResult getP2PInfo(DefraIdentity identity) {
        return GetP2PInfoNative(this.txnPtr, identity.getPointer());
    }

    public DefraResult listP2PActivePeers() {
        return ListP2PActivePeersNative(this.txnPtr, 0);
    }

    public DefraResult listP2PActivePeers(DefraIdentity identity) {
        return ListP2PActivePeersNative(this.txnPtr, identity.getPointer());
    }

    public DefraResult listP2PReplicators() {
        return ListP2PReplicatorsNative(this.txnPtr, 0);
    }

    public DefraResult listP2PReplicators(DefraIdentity identity) {
        return ListP2PReplicatorsNative(this.txnPtr, identity.getPointer());
    }

    public DefraResult addP2PReplicator(String collections, String addresses) {
        return AddP2PReplicatorNative(this.txnPtr, collections, addresses, 0);
    }

    public DefraResult addP2PReplicator(String collections, String addresses, DefraIdentity identity) {
        return AddP2PReplicatorNative(this.txnPtr, collections, addresses, identity.getPointer());
    }

    public DefraResult deleteP2PReplicator(String collections, String id) {
        return DeleteP2PReplicatorNative(this.txnPtr, collections, id, 0);
    }

    public DefraResult deleteP2PReplicator(String collections, String id, DefraIdentity identity) {
        return DeleteP2PReplicatorNative(this.txnPtr, collections, id, identity.getPointer());
    }

    public DefraResult addP2PCollection(String collections) {
        return AddP2PCollectionNative(this.txnPtr, collections, 0);
    }

    public DefraResult addP2PCollection(String collections, DefraIdentity identity) {
        return AddP2PCollectionNative(this.txnPtr, collections, identity.getPointer());
    }

    public DefraResult deleteP2PCollection(String collections) {
        return DeleteP2PCollectionNative(this.txnPtr, collections, 0);
    }

    public DefraResult deleteP2PCollection(String collections, DefraIdentity identity) {
        return DeleteP2PCollectionNative(this.txnPtr, collections, identity.getPointer());
    }

    public DefraResult listP2PCollections() {
        return ListP2PCollectionsNative(this.txnPtr, 0);
    }

    public DefraResult listP2PCollections(DefraIdentity identity) {
        return ListP2PCollectionsNative(this.txnPtr, identity.getPointer());
    }

    public DefraResult addP2PDocument(String collections) {
        return AddP2PDocumentNative(this.txnPtr, collections, 0);
    }

    public DefraResult addP2PDocument(String collections, DefraIdentity identity) {
        return AddP2PDocumentNative(this.txnPtr, collections, identity.getPointer());
    }

    public DefraResult deleteP2PDocument(String collections) {
        return DeleteP2PDocumentNative(this.txnPtr, collections, 0);
    }

    public DefraResult deleteP2PDocument(String collections, DefraIdentity identity) {
        return DeleteP2PDocumentNative(this.txnPtr, collections, identity.getPointer());
    }

    public DefraResult listP2PDocuments() {
        return ListP2PDocumentsNative(this.txnPtr, 0);
    }

    public DefraResult listP2PDocuments(DefraIdentity identity) {
        return ListP2PDocumentsNative(this.txnPtr, identity.getPointer());
    }

    public DefraResult syncP2PDocuments(String collection, String docIDs, String timeout) {
        return SyncP2PDocumentsNative(this.txnPtr, collection, docIDs, timeout, 0);
    }

    public DefraResult syncP2PDocuments(String collection, String docIDs, String timeout, DefraIdentity identity) {
        return SyncP2PDocumentsNative(this.txnPtr, collection, docIDs, timeout, identity.getPointer());
    }

    public DefraResult syncP2PCollectionVersions(String versionIDs, String timeout) {
        return SyncP2PCollectionVersionsNative(this.txnPtr, versionIDs, timeout, 0);
    }

    public DefraResult syncP2PCollectionVersions(String versionIDs, String timeout, DefraIdentity identity) {
        return SyncP2PCollectionVersionsNative(this.txnPtr, versionIDs, timeout, identity.getPointer());
    }

    public DefraResult syncP2PBranchableCollection(String collectionID, String timeout) {
        return SyncP2PBranchableCollectionNative(this.txnPtr, collectionID, timeout, 0);
    }

    public DefraResult syncP2PBranchableCollection(String collectionID, String timeout, DefraIdentity identity) {
        return SyncP2PBranchableCollectionNative(this.txnPtr, collectionID, timeout, identity.getPointer());
    }

    public DefraResult connectP2PPeers(String peerAddresses) {
        return ConnectP2PPeersNative(this.txnPtr, peerAddresses, 0);
    }

    public DefraResult connectP2PPeers(String peerAddresses, DefraIdentity identity) {
        return ConnectP2PPeersNative(this.txnPtr, peerAddresses, identity.getPointer());
    }

    // Query Methods
    public DefraResult executeQuery(String query, String operationName, String variables) {
        return ExecuteQueryNative(this.txnPtr, query, 0, operationName, variables);
    }

    public DefraResult executeQuery(String query, String operationName, String variables, DefraIdentity identity) {
        return ExecuteQueryNative(this.txnPtr, query, identity.getPointer(), operationName, variables);
    }

    // View Methods
    public DefraResult addView(String query, String sdl, String transformCID) {
        return AddViewNative(this.txnPtr, query, sdl, transformCID, 0);
    }

    public DefraResult addView(String query, String sdl, String transformCID, DefraIdentity identity) {
        return AddViewNative(this.txnPtr, query, sdl, transformCID, identity.getPointer());
    }

    public DefraResult refreshView(DefraCollectionOptions options) {
        return RefreshViewNative(this.txnPtr, options, 0);
    }

    public DefraResult refreshView(DefraCollectionOptions options, DefraIdentity identity) {
        return RefreshViewNative(this.txnPtr, options, identity.getPointer());
    }

    // Block Verification
    public DefraResult verifyBlockSignature(String keyType, String publicKey, String cid) {
        return VerifyBlockSignatureNative(this.txnPtr, keyType, publicKey, cid, 0);
    }

    public DefraResult verifyBlockSignature(String keyType, String publicKey, String cid, DefraIdentity identity) {
        return VerifyBlockSignatureNative(this.txnPtr, keyType, publicKey, cid, identity.getPointer());
    }

    public long getPointer() {
        return this.txnPtr;
    }
}