package source.defra;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

public class DefraNode {

    static {
		System.loadLibrary("nativewrapper");
        System.loadLibrary("defradb");
    }

    private native DefraNewNodeResult NewNodeNative(DefraNodeInitOptions options);
    private native DefraResult NodeCloseNative(long nodePtr);
    
    // ACP Methods
    private native DefraResult ACPAddDACPolicyNative(long nodePtr, long identityPtr, String policy);
    private native DefraResult ACPAddDACActorRelationshipNative(long nodePtr, long identityPtr, String collection, String docID, String relation, String actor);
    private native DefraResult ACPDeleteDACActorRelationshipNative(long nodePtr, long identityPtr, String collection, String docID, String relation, String actor);
    private native DefraResult ACPDisableNACNative(long nodePtr, long identityPtr);
    private native DefraResult ACPReEnableNACNative(long nodePtr, long identityPtr);
    private native DefraResult ACPAddNACActorRelationshipNative(long nodePtr, long identityPtr, String relation, String actor);
    private native DefraResult ACPDeleteNACActorRelationshipNative(long nodePtr, long identityPtr, String relation, String actor);
    private native DefraResult ACPGetNACStatusNative(long nodePtr, long identityPtr);
    
    // Collection Methods
    private native DefraResult AddCollectionNative(long nodePtr, String sdl, long identityPtr);
    private native DefraResult DescribeCollectionNative(long nodePtr, DefraCollectionOptions options, long identityPtr);
    private native DefraResult PatchCollectionNative(long nodePtr, String patch, String lensConfig, long identityPtr);
    private native DefraResult SetActiveCollectionNative(long nodePtr, DefraCollectionOptions options, long identityPtr);
    private native DefraResult TruncateCollectionNative(long nodePtr, DefraCollectionOptions options, long identityPtr);
    
    // Document Methods
    private native DefraResult AddDocumentNative(long nodePtr, String json, int isEncrypted, String encryptedFields, DefraCollectionOptions options, long identityPtr);
    private native DefraResult DeleteDocumentNative(long nodePtr, String docID, String filter, DefraCollectionOptions options, long identityPtr);
    private native DefraResult GetDocumentNative(long nodePtr, String docID, boolean showDeleted, DefraCollectionOptions options, long identityPtr);
    private native DefraResult UpdateDocumentNative(long nodePtr, String docID, String filter, String updater, DefraCollectionOptions options, long identityPtr);
    
    // Encrypted Index Methods
    private native DefraResult NewEncryptedIndexNative(long nodePtr, String collectionName, String fieldName, long identityPtr);
    private native DefraResult ListEncryptedIndexesNative(long nodePtr, String collectionName, long identityPtr);
    private native DefraResult DeleteEncryptedIndexNative(long nodePtr, String collectionName, String fieldName, long identityPtr);
    
    // Index Methods
    private native DefraResult NewIndexNative(long nodePtr, String indexName, String fields, boolean isUnique, DefraCollectionOptions options, long identityPtr);
    private native DefraResult ListIndexesNative(long nodePtr, DefraCollectionOptions options, long identityPtr);
    private native DefraResult DeleteIndexNative(long nodePtr, String indexName, DefraCollectionOptions options, long identityPtr);
    
    // Identity Methods
    private native DefraIdentityResult IdentityNewNative(String keyType);
    private native DefraResult GetNodeIdentityNative(long nodePtr);
    private native void FreeIdentityNative(long identityPtr);
    
    // Lens Methods
    private native DefraResult SetLensNative(long nodePtr, long identityPtr, String src, String dst, String cfg);
    private native DefraResult AddLensNative(long nodePtr, long identityPtr, String cfg);
    private native DefraResult ListLensesNative(long nodePtr, long identityPtr);
    
    // P2P Methods
    private native DefraResult GetP2PInfoNative(long nodePtr, long identityPtr);
    private native DefraResult ListP2PActivePeersNative(long nodePtr, long identityPtr);
    private native DefraResult ListP2PReplicatorsNative(long nodePtr, long identityPtr);
    private native DefraResult AddP2PReplicatorNative(long nodePtr, String collections, String addresses, long identityPtr);
    private native DefraResult DeleteP2PReplicatorNative(long nodePtr, String collections, String id, long identityPtr);
    private native DefraResult AddP2PCollectionNative(long nodePtr, String collections, long identityPtr);
    private native DefraResult DeleteP2PCollectionNative(long nodePtr, String collections, long identityPtr);
    private native DefraResult ListP2PCollectionsNative(long nodePtr, long identityPtr);
    private native DefraResult AddP2PDocumentNative(long nodePtr, String collections, long identityPtr);
    private native DefraResult DeleteP2PDocumentNative(long nodePtr, String collections, long identityPtr);
    private native DefraResult ListP2PDocumentsNative(long nodePtr, long identityPtr);
    private native DefraResult SyncP2PDocumentsNative(long nodePtr, String collection, String docIDs, String timeout, long identityPtr);
    private native DefraResult SyncP2PCollectionVersionsNative(long nodePtr, String versionIDs, String timeout, long identityPtr);
    private native DefraResult SyncP2PBranchableCollectionNative(long nodePtr, String collectionID, String timeout, long identityPtr);
    private native DefraResult ConnectP2PPeersNative(long nodePtr, String peerAddresses, long identityPtr);
    
    // Query Methods
    private native DefraResult ExecuteQueryNative(long nodePtr, String query, long identityPtr, String operationName, String variables);
    private native DefraResult PollSubscriptionNative(String id);
    private native DefraResult CloseSubscriptionNative(String id);
    
    // Version Method
    private native DefraResult GetVersionNative(boolean flagFull, boolean flagJSON);
    
    // View Methods
    private native DefraResult AddViewNative(long nodePtr, String query, String sdl, String transformCID, long identityPtr);
    private native DefraResult RefreshViewNative(long nodePtr, DefraCollectionOptions options, long identityPtr);
    
    // Block Verification
    private native DefraResult VerifyBlockSignatureNative(long nodePtr, String keyType, String publicKey, String cid, long identityPtr);
    
    // Transaction Methods
    private native DefraTransactionResult TransactionCreateNative(long nodePtr, boolean isConcurrent, boolean isReadOnly);
    
    private long nodePtr;

    public DefraNode(DefraNodeInitOptions options) {
        DefraNewNodeResult result = NewNodeNative(options);
        if (result.status != 0) {
            this.nodePtr = 0;
            Log.e("DefraNode", "Error creating node: " + result.error);
            return;
        }
        this.nodePtr = result.nodePtr;
    }

    public DefraResult close() {
        return NodeCloseNative(this.nodePtr);
    }

    // ACP Methods
    public DefraResult ACPAddDACPolicy(String policy) {
        return ACPAddDACPolicyNative(this.nodePtr, 0, policy);
    }

    public DefraResult ACPAddDACPolicy(String policy, DefraIdentity identity) {
        return ACPAddDACPolicyNative(this.nodePtr, identity.getPointer(), policy);
    }

    public DefraResult ACPAddDACActorRelationship(String collection, String docID, String relation, String actor) {
        return ACPAddDACActorRelationshipNative(this.nodePtr, 0, collection, docID, relation, actor);
    }

    public DefraResult ACPAddDACActorRelationship(String collection, String docID, String relation, String actor, DefraIdentity identity) {
        return ACPAddDACActorRelationshipNative(this.nodePtr, identity.getPointer(), collection, docID, relation, actor);
    }   

    public DefraResult ACPDeleteDACActorRelationship(String collection, String docID, String relation, String actor) {
        return ACPDeleteDACActorRelationshipNative(this.nodePtr, 0, collection, docID, relation, actor);
    }

    public DefraResult ACPDeleteDACActorRelationship(String collection, String docID, String relation, String actor, DefraIdentity identity) {
        return ACPDeleteDACActorRelationshipNative(this.nodePtr, identity.getPointer(), collection, docID, relation, actor);
    }

    public DefraResult ACPDisableNAC() {
        return ACPDisableNACNative(this.nodePtr, 0);
    }

    public DefraResult ACPDisableNAC(DefraIdentity identity) {
        return ACPDisableNACNative(this.nodePtr, identity.getPointer());
    }

    public DefraResult ACPReEnableNAC() {
        return ACPReEnableNACNative(this.nodePtr, 0);
    }

    public DefraResult ACPReEnableNAC(DefraIdentity identity) {
        return ACPReEnableNACNative(this.nodePtr, identity.getPointer());
    }

    public DefraResult ACPAddNACActorRelationship(String relation, String actor) {
        return ACPAddNACActorRelationshipNative(this.nodePtr, 0, relation, actor);
    }

    public DefraResult ACPAddNACActorRelationship(String relation, String actor, DefraIdentity identity) {
        return ACPAddNACActorRelationshipNative(this.nodePtr, identity.getPointer(), relation, actor);
    }

    public DefraResult ACPDeleteNACActorRelationship(String relation, String actor) {
        return ACPDeleteNACActorRelationshipNative(this.nodePtr, 0, relation, actor);
    }

    public DefraResult ACPDeleteNACActorRelationship(String relation, String actor, DefraIdentity identity) {
        return ACPDeleteNACActorRelationshipNative(this.nodePtr, identity.getPointer(), relation, actor);
    }

    public DefraResult ACPGetNACStatus() {
        return ACPGetNACStatusNative(this.nodePtr, 0);
    }

    public DefraResult ACPGetNACStatus(DefraIdentity identity) {
        return ACPGetNACStatusNative(this.nodePtr, identity.getPointer());
    }

    // Collection Methods
    public DefraResult addCollection(String sdl) {
        return AddCollectionNative(this.nodePtr, sdl, 0);
    }

    public DefraResult addCollection(String sdl, DefraIdentity identity) {
        return AddCollectionNative(this.nodePtr, sdl, identity.getPointer());
    }

    public DefraResult describeCollection(DefraCollectionOptions options) {
        return DescribeCollectionNative(this.nodePtr, options, 0);
    }
	
	public DefraCollection getCollectionByName(String name) throws Exception {
		DefraCollectionOptions copts = new DefraCollectionOptions();
		copts.name = name;
		DefraResult describeResult = DescribeCollectionNative(this.nodePtr, copts, 0);
		if (describeResult.status != 0) {
			throw new Exception("Collection with the name " + name + " was not found.");
		}
		JSONArray array = new JSONArray(describeResult.value);
		JSONObject first = array.getJSONObject(0);
		String collectionID = first.getString("CollectionID");
		String versionID = first.getString("VersionID");
		return new DefraCollection(this.nodePtr, name, collectionID, versionID);		
	}
	
	public DefraCollection getCollectionByName(String name, DefraIdentity identity) throws Exception {
		DefraCollectionOptions copts = new DefraCollectionOptions();
		copts.name = name;
		DefraResult describeResult = DescribeCollectionNative(this.nodePtr, copts, identity.getPointer());
		if (describeResult.status != 0) {
			throw new Exception("Collection with the name " + name + " was not found.");
		}
		JSONArray array = new JSONArray(describeResult.value);
		JSONObject first = array.getJSONObject(0);
		String collectionID = first.getString("CollectionID");
		String versionID = first.getString("VersionID");
		return new DefraCollection(this.nodePtr, name, collectionID, versionID);		
	}

    public DefraResult describeCollection(DefraCollectionOptions options, DefraIdentity identity) {
        return DescribeCollectionNative(this.nodePtr, options, identity.getPointer());
    }

    public DefraResult patchCollection(String patch, String lensConfig) {
        return PatchCollectionNative(this.nodePtr, patch, lensConfig, 0);
    }

    public DefraResult patchCollection(String patch, String lensConfig, DefraIdentity identity) {
        return PatchCollectionNative(this.nodePtr, patch, lensConfig, identity.getPointer());
    }

    public DefraResult setActiveCollection(DefraCollectionOptions options) {
        return SetActiveCollectionNative(this.nodePtr, options, 0);
    }

    public DefraResult setActiveCollection(DefraCollectionOptions options, DefraIdentity identity) {
        return SetActiveCollectionNative(this.nodePtr, options, identity.getPointer());
    }

    public DefraResult truncateCollection(DefraCollectionOptions options) {
        return TruncateCollectionNative(this.nodePtr, options, 0);
    }

    public DefraResult truncateCollection(DefraCollectionOptions options, DefraIdentity identity) {
        return TruncateCollectionNative(this.nodePtr, options, identity.getPointer());
    }

    // Document Methods
    public DefraResult addDocument(String json, boolean isEncrypted, String encryptedFields, DefraCollectionOptions options) {
        return AddDocumentNative(this.nodePtr, json, isEncrypted ? 1 : 0, encryptedFields, options, 0);
    }

    public DefraResult addDocument(String json, boolean isEncrypted, String encryptedFields, DefraCollectionOptions options, DefraIdentity identity) {
        return AddDocumentNative(this.nodePtr, json, isEncrypted ? 1 : 0, encryptedFields, options, identity.getPointer());
    }

    public DefraResult deleteDocument(String docID, String filter, DefraCollectionOptions options) {
        return DeleteDocumentNative(this.nodePtr, docID, filter, options, 0);
    }

    public DefraResult deleteDocument(String docID, String filter, DefraCollectionOptions options, DefraIdentity identity) {
        return DeleteDocumentNative(this.nodePtr, docID, filter, options, identity.getPointer());
    }

    public DefraResult getDocument(String docID, boolean showDeleted, DefraCollectionOptions options) {
        return GetDocumentNative(this.nodePtr, docID, showDeleted, options, 0);
    }

    public DefraResult getDocument(String docID, boolean showDeleted, DefraCollectionOptions options, DefraIdentity identity) {
        return GetDocumentNative(this.nodePtr, docID, showDeleted, options, identity.getPointer());
    }

    public DefraResult updateDocument(String docID, String filter, String updater, DefraCollectionOptions options) {
        return UpdateDocumentNative(this.nodePtr, docID, filter, updater, options, 0);
    }

    public DefraResult updateDocument(String docID, String filter, String updater, DefraCollectionOptions options, DefraIdentity identity) {
        return UpdateDocumentNative(this.nodePtr, docID, filter, updater, options, identity.getPointer());
    }

    // Encrypted Index Methods
    public DefraResult newEncryptedIndex(String collectionName, String fieldName) {
        return NewEncryptedIndexNative(this.nodePtr, collectionName, fieldName, 0);
    }

    public DefraResult newEncryptedIndex(String collectionName, String fieldName, DefraIdentity identity) {
        return NewEncryptedIndexNative(this.nodePtr, collectionName, fieldName, identity.getPointer());
    }

    public DefraResult listEncryptedIndexes(String collectionName) {
        return ListEncryptedIndexesNative(this.nodePtr, collectionName, 0);
    }

    public DefraResult listEncryptedIndexes(String collectionName, DefraIdentity identity) {
        return ListEncryptedIndexesNative(this.nodePtr, collectionName, identity.getPointer());
    }

    public DefraResult deleteEncryptedIndex(String collectionName, String fieldName) {
        return DeleteEncryptedIndexNative(this.nodePtr, collectionName, fieldName, 0);
    }

    public DefraResult deleteEncryptedIndex(String collectionName, String fieldName, DefraIdentity identity) {
        return DeleteEncryptedIndexNative(this.nodePtr, collectionName, fieldName, identity.getPointer());
    }

    // Index Methods
    public DefraResult newIndex(String indexName, String fields, boolean isUnique, DefraCollectionOptions options) {
        return NewIndexNative(this.nodePtr, indexName, fields, isUnique, options, 0);
    }

    public DefraResult newIndex(String indexName, String fields, boolean isUnique, DefraCollectionOptions options, DefraIdentity identity) {
        return NewIndexNative(this.nodePtr, indexName, fields, isUnique, options, identity.getPointer());
    }

    public DefraResult listIndexes(DefraCollectionOptions options) {
        return ListIndexesNative(this.nodePtr, options, 0);
    }

    public DefraResult listIndexes(DefraCollectionOptions options, DefraIdentity identity) {
        return ListIndexesNative(this.nodePtr, options, identity.getPointer());
    }

    public DefraResult deleteIndex(String indexName, DefraCollectionOptions options) {
        return DeleteIndexNative(this.nodePtr, indexName, options, 0);
    }

    public DefraResult deleteIndex(String indexName, DefraCollectionOptions options, DefraIdentity identity) {
        return DeleteIndexNative(this.nodePtr, indexName, options, identity.getPointer());
    }

    // Identity Methods
    public DefraIdentity identityNew(String keyType) {
        DefraIdentityResult identResult = IdentityNewNative(keyType);
        if (identResult.status != 0) {
            Log.e("DefraNode", "Error creating identity: " + identResult.error);
            return new DefraIdentity(0);
        }
        return new DefraIdentity(identResult.identityPtr);
    }

    public DefraResult getNodeIdentity() {
        return GetNodeIdentityNative(this.nodePtr);
    }

    public void freeIdentity(DefraIdentity identity) {
        FreeIdentityNative(identity.getPointer());
    }

    // Lens Methods
    public DefraResult setLens(String src, String dst, String cfg) {
        return SetLensNative(this.nodePtr, 0, src, dst, cfg);
    }

    public DefraResult setLens(String src, String dst, String cfg, DefraIdentity identity) {
        return SetLensNative(this.nodePtr, identity.getPointer(), src, dst, cfg);
    }

    public DefraResult addLens(String cfg) {
        return AddLensNative(this.nodePtr, 0, cfg);
    }

    public DefraResult addLens(String cfg, DefraIdentity identity) {
        return AddLensNative(this.nodePtr, identity.getPointer(), cfg);
    }

    public DefraResult listLenses() {
        return ListLensesNative(this.nodePtr, 0);
    }

    public DefraResult listLenses(DefraIdentity identity) {
        return ListLensesNative(this.nodePtr, identity.getPointer());
    }

    // P2P Methods
    public DefraResult getP2PInfo() {
        return GetP2PInfoNative(this.nodePtr, 0);
    }

    public DefraResult getP2PInfo(DefraIdentity identity) {
        return GetP2PInfoNative(this.nodePtr, identity.getPointer());
    }

    public DefraResult listP2PActivePeers() {
        return ListP2PActivePeersNative(this.nodePtr, 0);
    }

    public DefraResult listP2PActivePeers(DefraIdentity identity) {
        return ListP2PActivePeersNative(this.nodePtr, identity.getPointer());
    }

    public DefraResult listP2PReplicators() {
        return ListP2PReplicatorsNative(this.nodePtr, 0);
    }

    public DefraResult listP2PReplicators(DefraIdentity identity) {
        return ListP2PReplicatorsNative(this.nodePtr, identity.getPointer());
    }

    public DefraResult addP2PReplicator(String collections, String addresses) {
        return AddP2PReplicatorNative(this.nodePtr, collections, addresses, 0);
    }

    public DefraResult addP2PReplicator(String collections, String addresses, DefraIdentity identity) {
        return AddP2PReplicatorNative(this.nodePtr, collections, addresses, identity.getPointer());
    }

    public DefraResult deleteP2PReplicator(String collections, String id) {
        return DeleteP2PReplicatorNative(this.nodePtr, collections, id, 0);
    }

    public DefraResult deleteP2PReplicator(String collections, String id, DefraIdentity identity) {
        return DeleteP2PReplicatorNative(this.nodePtr, collections, id, identity.getPointer());
    }

    public DefraResult addP2PCollection(String collections) {
        return AddP2PCollectionNative(this.nodePtr, collections, 0);
    }

    public DefraResult addP2PCollection(String collections, DefraIdentity identity) {
        return AddP2PCollectionNative(this.nodePtr, collections, identity.getPointer());
    }

    public DefraResult deleteP2PCollection(String collections) {
        return DeleteP2PCollectionNative(this.nodePtr, collections, 0);
    }

    public DefraResult deleteP2PCollection(String collections, DefraIdentity identity) {
        return DeleteP2PCollectionNative(this.nodePtr, collections, identity.getPointer());
    }

    public DefraResult listP2PCollections() {
        return ListP2PCollectionsNative(this.nodePtr, 0);
    }

    public DefraResult listP2PCollections(DefraIdentity identity) {
        return ListP2PCollectionsNative(this.nodePtr, identity.getPointer());
    }

    public DefraResult addP2PDocument(String collections) {
        return AddP2PDocumentNative(this.nodePtr, collections, 0);
    }

    public DefraResult addP2PDocument(String collections, DefraIdentity identity) {
        return AddP2PDocumentNative(this.nodePtr, collections, identity.getPointer());
    }

    public DefraResult deleteP2PDocument(String collections) {
        return DeleteP2PDocumentNative(this.nodePtr, collections, 0);
    }

    public DefraResult deleteP2PDocument(String collections, DefraIdentity identity) {
        return DeleteP2PDocumentNative(this.nodePtr, collections, identity.getPointer());
    }

    public DefraResult listP2PDocuments() {
        return ListP2PDocumentsNative(this.nodePtr, 0);
    }

    public DefraResult listP2PDocuments(DefraIdentity identity) {
        return ListP2PDocumentsNative(this.nodePtr, identity.getPointer());
    }

    public DefraResult syncP2PDocuments(String collection, String docIDs, String timeout) {
        return SyncP2PDocumentsNative(this.nodePtr, collection, docIDs, timeout, 0);
    }

    public DefraResult syncP2PDocuments(String collection, String docIDs, String timeout, DefraIdentity identity) {
        return SyncP2PDocumentsNative(this.nodePtr, collection, docIDs, timeout, identity.getPointer());
    }

    public DefraResult syncP2PCollectionVersions(String versionIDs, String timeout) {
        return SyncP2PCollectionVersionsNative(this.nodePtr, versionIDs, timeout, 0);
    }

    public DefraResult syncP2PCollectionVersions(String versionIDs, String timeout, DefraIdentity identity) {
        return SyncP2PCollectionVersionsNative(this.nodePtr, versionIDs, timeout, identity.getPointer());
    }

    public DefraResult syncP2PBranchableCollection(String collectionID, String timeout) {
        return SyncP2PBranchableCollectionNative(this.nodePtr, collectionID, timeout, 0);
    }

    public DefraResult syncP2PBranchableCollection(String collectionID, String timeout, DefraIdentity identity) {
        return SyncP2PBranchableCollectionNative(this.nodePtr, collectionID, timeout, identity.getPointer());
    }

    public DefraResult connectP2PPeers(String peerAddresses) {
        return ConnectP2PPeersNative(this.nodePtr, peerAddresses, 0);
    }

    public DefraResult connectP2PPeers(String peerAddresses, DefraIdentity identity) {
        return ConnectP2PPeersNative(this.nodePtr, peerAddresses, identity.getPointer());
    }

    // Query Methods
    public DefraResult executeQuery(String query, String operationName, String variables) {
        return ExecuteQueryNative(this.nodePtr, query, 0, operationName, variables);
    }

    public DefraResult executeQuery(String query, String operationName, String variables, DefraIdentity identity) {
        return ExecuteQueryNative(this.nodePtr, query, identity.getPointer(), operationName, variables);
    }

    public DefraResult pollSubscription(String id) {
        return PollSubscriptionNative(id);
    }

    public DefraResult closeSubscription(String id) {
        return CloseSubscriptionNative(id);
    }

    // Version Method
    public DefraResult getVersion(boolean flagFull, boolean flagJSON) {
        return GetVersionNative(flagFull, flagJSON);
    }

    // View Methods
    public DefraResult addView(String query, String sdl, String transformCID) {
        return AddViewNative(this.nodePtr, query, sdl, transformCID, 0);
    }

    public DefraResult addView(String query, String sdl, String transformCID, DefraIdentity identity) {
        return AddViewNative(this.nodePtr, query, sdl, transformCID, identity.getPointer());
    }

    public DefraResult refreshView(DefraCollectionOptions options) {
        return RefreshViewNative(this.nodePtr, options, 0);
    }

    public DefraResult refreshView(DefraCollectionOptions options, DefraIdentity identity) {
        return RefreshViewNative(this.nodePtr, options, identity.getPointer());
    }

    // Block Verification
    public DefraResult verifyBlockSignature(String keyType, String publicKey, String cid) {
        return VerifyBlockSignatureNative(this.nodePtr, keyType, publicKey, cid, 0);
    }

    public DefraResult verifyBlockSignature(String keyType, String publicKey, String cid, DefraIdentity identity) {
        return VerifyBlockSignatureNative(this.nodePtr, keyType, publicKey, cid, identity.getPointer());
    }

    // Transaction Methods
    public DefraTransaction transactionCreate(boolean isConcurrent, boolean isReadOnly) {
        DefraTransactionResult txnResult = TransactionCreateNative(this.nodePtr, isConcurrent, isReadOnly);
        if (txnResult.status != 0) {
            Log.e("DefraNode", "Error creating transaction: " + txnResult.error);
            return new DefraTransaction(0);
        }
        return new DefraTransaction(txnResult.txnPtr);
    }

    public long getPointer() {
        return this.nodePtr;
    }
}