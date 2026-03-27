package source.defra;

public class DefraCollection {

    static {
		System.loadLibrary("nativewrapper");
        System.loadLibrary("defradb");
    }
	
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
    
	private String name;
	private String collectionID;
	private String versionID;
	private long storePtr;
	
	public DefraCollection(long ptr, String n, String c, String v) {
		this.storePtr = ptr;
		this.name = n;
		this.collectionID = c;
		this.versionID = v;
	}
	
	private DefraCollectionOptions collectionOptionsFromThis() {
		DefraCollectionOptions opts = new DefraCollectionOptions();
		opts.version = this.versionID;
		opts.collectionID = this.collectionID;
		opts.name = this.name;
		return opts;
	}
	
	public String Name() {
		return this.name;
	}
	
	public String VersionID() {
		return this.versionID;
	}
	
	public String CollectionID() {
		return this.collectionID;
	}
	
    // Document Methods
    public DefraResult addDocument(String json, boolean isEncrypted, String encryptedFields) {
		
        return AddDocumentNative(this.storePtr, json, isEncrypted ? 1 : 0, encryptedFields, collectionOptionsFromThis(), 0);
    }

    public DefraResult addDocument(String json, boolean isEncrypted, String encryptedFields, DefraIdentity identity) {
        return AddDocumentNative(this.storePtr, json, isEncrypted ? 1 : 0, encryptedFields, collectionOptionsFromThis(), identity.getPointer());
    }
	
	public DefraResult deleteDocument(String docID, String filter) {
        return DeleteDocumentNative(this.storePtr, docID, filter, collectionOptionsFromThis(), 0);
    }

    public DefraResult deleteDocument(String docID, String filter, DefraCollectionOptions options, DefraIdentity identity) {
        return DeleteDocumentNative(this.storePtr, docID, filter, collectionOptionsFromThis(), identity.getPointer());
    }

    public DefraResult getDocument(String docID, boolean showDeleted) {
        return GetDocumentNative(this.storePtr, docID, showDeleted, collectionOptionsFromThis(), 0);
    }

    public DefraResult getDocument(String docID, boolean showDeleted, DefraIdentity identity) {
        return GetDocumentNative(this.storePtr, docID, showDeleted, collectionOptionsFromThis(), identity.getPointer());
    }

    public DefraResult updateDocument(String docID, String filter, String updater) {
        return UpdateDocumentNative(this.storePtr, docID, filter, updater, collectionOptionsFromThis(), 0);
    }

    public DefraResult updateDocument(String docID, String filter, String updater, DefraIdentity identity) {
        return UpdateDocumentNative(this.storePtr, docID, filter, updater, collectionOptionsFromThis(), identity.getPointer());
    }

    // Encrypted Index Methods
    public DefraResult newEncryptedIndex(String collectionName, String fieldName) {
        return NewEncryptedIndexNative(this.storePtr, collectionName, fieldName, 0);
    }

    public DefraResult newEncryptedIndex(String collectionName, String fieldName, DefraIdentity identity) {
        return NewEncryptedIndexNative(this.storePtr, collectionName, fieldName, identity.getPointer());
    }

    public DefraResult listEncryptedIndexes(String collectionName) {
        return ListEncryptedIndexesNative(this.storePtr, collectionName, 0);
    }

    public DefraResult listEncryptedIndexes(String collectionName, DefraIdentity identity) {
        return ListEncryptedIndexesNative(this.storePtr, collectionName, identity.getPointer());
    }

    public DefraResult deleteEncryptedIndex(String collectionName, String fieldName) {
        return DeleteEncryptedIndexNative(this.storePtr, collectionName, fieldName, 0);
    }

    public DefraResult deleteEncryptedIndex(String collectionName, String fieldName, DefraIdentity identity) {
        return DeleteEncryptedIndexNative(this.storePtr, collectionName, fieldName, identity.getPointer());
    }

    // Index Methods
    public DefraResult newIndex(String indexName, String fields, boolean isUnique) {
        return NewIndexNative(this.storePtr, indexName, fields, isUnique, collectionOptionsFromThis(), 0);
    }

    public DefraResult newIndex(String indexName, String fields, boolean isUnique, DefraIdentity identity) {
        return NewIndexNative(this.storePtr, indexName, fields, isUnique, collectionOptionsFromThis(), identity.getPointer());
    }

    public DefraResult listIndexes() {
        return ListIndexesNative(this.storePtr, collectionOptionsFromThis(), 0);
    }

    public DefraResult listIndexes(DefraIdentity identity) {
        return ListIndexesNative(this.storePtr, collectionOptionsFromThis(), identity.getPointer());
    }

    public DefraResult deleteIndex(String indexName) {
        return DeleteIndexNative(this.storePtr, indexName, collectionOptionsFromThis(), 0);
    }

    public DefraResult deleteIndex(String indexName, DefraIdentity identity) {
        return DeleteIndexNative(this.storePtr, indexName, collectionOptionsFromThis(), identity.getPointer());
    }
	
	
}