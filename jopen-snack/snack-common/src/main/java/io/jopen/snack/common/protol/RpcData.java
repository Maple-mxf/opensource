package io.jopen.snack.common.protol;

public final class RpcData {
    private RpcData() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistry registry) {
        registerAllExtensions(
                (com.google.protobuf.ExtensionRegistryLite) registry);
    }

    public interface C2SOrBuilder extends
            // @@protoc_insertion_point(interface_extends:io.jopen.snack.common.protol.C2S)
            com.google.protobuf.MessageOrBuilder {

        /**
         * <pre>
         * 条件体
         * </pre>
         *
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        java.util.List<com.google.protobuf.Any>
        getConditionsList();

        /**
         * <pre>
         * 条件体
         * </pre>
         *
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        com.google.protobuf.Any getConditions(int index);

        /**
         * <pre>
         * 条件体
         * </pre>
         *
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        int getConditionsCount();

        /**
         * <pre>
         * 条件体
         * </pre>
         *
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        java.util.List<? extends com.google.protobuf.AnyOrBuilder>
        getConditionsOrBuilderList();

        /**
         * <pre>
         * 条件体
         * </pre>
         *
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        com.google.protobuf.AnyOrBuilder getConditionsOrBuilder(
                int index);

        /**
         * <pre>
         * 实体对象
         * </pre>
         *
         * <code>repeated .google.protobuf.Any rows = 2;</code>
         */
        java.util.List<com.google.protobuf.Any>
        getRowsList();

        /**
         * <pre>
         * 实体对象
         * </pre>
         *
         * <code>repeated .google.protobuf.Any rows = 2;</code>
         */
        com.google.protobuf.Any getRows(int index);

        /**
         * <pre>
         * 实体对象
         * </pre>
         *
         * <code>repeated .google.protobuf.Any rows = 2;</code>
         */
        int getRowsCount();

        /**
         * <pre>
         * 实体对象
         * </pre>
         *
         * <code>repeated .google.protobuf.Any rows = 2;</code>
         */
        java.util.List<? extends com.google.protobuf.AnyOrBuilder>
        getRowsOrBuilderList();

        /**
         * <pre>
         * 实体对象
         * </pre>
         *
         * <code>repeated .google.protobuf.Any rows = 2;</code>
         */
        com.google.protobuf.AnyOrBuilder getRowsOrBuilder(
                int index);

        /**
         * <pre>
         * 操作级别
         * </pre>
         *
         * <code>.io.jopen.snack.common.protol.C2S.OperationLevel operationLevel = 3;</code>
         *
         * @return The enum numeric value on the wire for operationLevel.
         */
        int getOperationLevelValue();

        /**
         * <pre>
         * 操作级别
         * </pre>
         *
         * <code>.io.jopen.snack.common.protol.C2S.OperationLevel operationLevel = 3;</code>
         *
         * @return The operationLevel.
         */
        C2S.OperationLevel getOperationLevel();

        /**
         * <pre>
         * 数据库操作符
         * </pre>
         *
         * <code>.io.jopen.snack.common.protol.C2S.DBOperation dbOperation = 4;</code>
         *
         * @return The enum numeric value on the wire for dbOperation.
         */
        int getDbOperationValue();

        /**
         * <pre>
         * 数据库操作符
         * </pre>
         *
         * <code>.io.jopen.snack.common.protol.C2S.DBOperation dbOperation = 4;</code>
         *
         * @return The dbOperation.
         */
        C2S.DBOperation getDbOperation();

        /**
         * <pre>
         * 表格操作符
         * </pre>
         *
         * <code>.io.jopen.snack.common.protol.C2S.TableOperation tableOperation = 5;</code>
         *
         * @return The enum numeric value on the wire for tableOperation.
         */
        int getTableOperationValue();

        /**
         * <pre>
         * 表格操作符
         * </pre>
         *
         * <code>.io.jopen.snack.common.protol.C2S.TableOperation tableOperation = 5;</code>
         *
         * @return The tableOperation.
         */
        C2S.TableOperation getTableOperation();

        /**
         * <pre>
         * 行操作符
         * </pre>
         *
         * <code>.io.jopen.snack.common.protol.C2S.RowOperation rowOption = 6;</code>
         *
         * @return The enum numeric value on the wire for rowOption.
         */
        int getRowOptionValue();

        /**
         * <pre>
         * 行操作符
         * </pre>
         *
         * <code>.io.jopen.snack.common.protol.C2S.RowOperation rowOption = 6;</code>
         *
         * @return The rowOption.
         */
        C2S.RowOperation getRowOption();
    }

    /**
     * Protobuf type {@code io.jopen.snack.common.protol.C2S}
     */
    public static final class C2S extends
            com.google.protobuf.GeneratedMessageV3 implements
            // @@protoc_insertion_point(message_implements:io.jopen.snack.common.protol.C2S)
            C2SOrBuilder {
        private static final long serialVersionUID = 0L;

        // Use C2S.newBuilder() to construct.
        private C2S(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private C2S() {
            conditions_ = java.util.Collections.emptyList();
            rows_ = java.util.Collections.emptyList();
            operationLevel_ = 0;
            dbOperation_ = 0;
            tableOperation_ = 0;
            rowOption_ = 0;
        }

        @Override
        @SuppressWarnings({"unused"})
        protected Object newInstance(
                UnusedPrivateParameter unused) {
            return new C2S();
        }

        @Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }

        private C2S(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10: {
                            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                                conditions_ = new java.util.ArrayList<com.google.protobuf.Any>();
                                mutable_bitField0_ |= 0x00000001;
                            }
                            conditions_.add(
                                    input.readMessage(com.google.protobuf.Any.parser(), extensionRegistry));
                            break;
                        }
                        case 18: {
                            if (!((mutable_bitField0_ & 0x00000002) != 0)) {
                                rows_ = new java.util.ArrayList<com.google.protobuf.Any>();
                                mutable_bitField0_ |= 0x00000002;
                            }
                            rows_.add(
                                    input.readMessage(com.google.protobuf.Any.parser(), extensionRegistry));
                            break;
                        }
                        case 24: {
                            int rawValue = input.readEnum();

                            operationLevel_ = rawValue;
                            break;
                        }
                        case 32: {
                            int rawValue = input.readEnum();

                            dbOperation_ = rawValue;
                            break;
                        }
                        case 40: {
                            int rawValue = input.readEnum();

                            tableOperation_ = rawValue;
                            break;
                        }
                        case 48: {
                            int rawValue = input.readEnum();

                            rowOption_ = rawValue;
                            break;
                        }
                        default: {
                            if (!parseUnknownField(
                                    input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e).setUnfinishedMessage(this);
            } finally {
                if (((mutable_bitField0_ & 0x00000001) != 0)) {
                    conditions_ = java.util.Collections.unmodifiableList(conditions_);
                }
                if (((mutable_bitField0_ & 0x00000002) != 0)) {
                    rows_ = java.util.Collections.unmodifiableList(rows_);
                }
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }

        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return RpcData.internal_static_io_jopen_snack_common_protol_C2S_descriptor;
        }

        @Override
        protected FieldAccessorTable
        internalGetFieldAccessorTable() {
            return RpcData.internal_static_io_jopen_snack_common_protol_C2S_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            C2S.class, Builder.class);
        }

        /**
         * <pre>
         * 操作级别
         * </pre>
         * <p>
         * Protobuf enum {@code io.jopen.snack.common.protol.C2S.OperationLevel}
         */
        public enum OperationLevel
                implements com.google.protobuf.ProtocolMessageEnum {
            /**
             * <code>database = 0;</code>
             */
            database(0),
            /**
             * <code>table = 1;</code>
             */
            table(1),
            /**
             * <code>row = 2;</code>
             */
            row(2),
            UNRECOGNIZED(-1),
            ;

            /**
             * <code>database = 0;</code>
             */
            public static final int database_VALUE = 0;
            /**
             * <code>table = 1;</code>
             */
            public static final int table_VALUE = 1;
            /**
             * <code>row = 2;</code>
             */
            public static final int row_VALUE = 2;


            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException(
                            "Can't get the number of an unknown enum value.");
                }
                return value;
            }

            /**
             * @param value The numeric wire value of the corresponding enum entry.
             * @return The enum associated with the given numeric wire value.
             * @deprecated Use {@link #forNumber(int)} instead.
             */
            @Deprecated
            public static OperationLevel valueOf(int value) {
                return forNumber(value);
            }

            /**
             * @param value The numeric wire value of the corresponding enum entry.
             * @return The enum associated with the given numeric wire value.
             */
            public static OperationLevel forNumber(int value) {
                switch (value) {
                    case 0:
                        return database;
                    case 1:
                        return table;
                    case 2:
                        return row;
                    default:
                        return null;
                }
            }

            public static com.google.protobuf.Internal.EnumLiteMap<OperationLevel>
            internalGetValueMap() {
                return internalValueMap;
            }

            private static final com.google.protobuf.Internal.EnumLiteMap<
                    OperationLevel> internalValueMap =
                    new com.google.protobuf.Internal.EnumLiteMap<OperationLevel>() {
                        public OperationLevel findValueByNumber(int number) {
                            return OperationLevel.forNumber(number);
                        }
                    };

            public final com.google.protobuf.Descriptors.EnumValueDescriptor
            getValueDescriptor() {
                return getDescriptor().getValues().get(ordinal());
            }

            public final com.google.protobuf.Descriptors.EnumDescriptor
            getDescriptorForType() {
                return getDescriptor();
            }

            public static final com.google.protobuf.Descriptors.EnumDescriptor
            getDescriptor() {
                return C2S.getDescriptor().getEnumTypes().get(0);
            }

            private static final OperationLevel[] VALUES = values();

            public static OperationLevel valueOf(
                    com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException(
                            "EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private final int value;

            private OperationLevel(int value) {
                this.value = value;
            }

            // @@protoc_insertion_point(enum_scope:io.jopen.snack.common.protol.C2S.OperationLevel)
        }

        /**
         * <pre>
         * 数据库的操作符
         * </pre>
         * <p>
         * Protobuf enum {@code io.jopen.snack.common.protol.C2S.DBOperation}
         */
        public enum DBOperation
                implements com.google.protobuf.ProtocolMessageEnum {
            /**
             * <pre>
             * 创建
             * </pre>
             *
             * <code>createDB = 0;</code>
             */
            createDB(0),
            /**
             * <pre>
             * 删除
             * </pre>
             *
             * <code>dropDB = 1;</code>
             */
            dropDB(1),
            /**
             * <pre>
             * 修改
             * </pre>
             *
             * <code>modifyDB = 2;</code>
             */
            modifyDB(2),
            UNRECOGNIZED(-1),
            ;

            /**
             * <pre>
             * 创建
             * </pre>
             *
             * <code>createDB = 0;</code>
             */
            public static final int createDB_VALUE = 0;
            /**
             * <pre>
             * 删除
             * </pre>
             *
             * <code>dropDB = 1;</code>
             */
            public static final int dropDB_VALUE = 1;
            /**
             * <pre>
             * 修改
             * </pre>
             *
             * <code>modifyDB = 2;</code>
             */
            public static final int modifyDB_VALUE = 2;


            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException(
                            "Can't get the number of an unknown enum value.");
                }
                return value;
            }

            /**
             * @param value The numeric wire value of the corresponding enum entry.
             * @return The enum associated with the given numeric wire value.
             * @deprecated Use {@link #forNumber(int)} instead.
             */
            @Deprecated
            public static DBOperation valueOf(int value) {
                return forNumber(value);
            }

            /**
             * @param value The numeric wire value of the corresponding enum entry.
             * @return The enum associated with the given numeric wire value.
             */
            public static DBOperation forNumber(int value) {
                switch (value) {
                    case 0:
                        return createDB;
                    case 1:
                        return dropDB;
                    case 2:
                        return modifyDB;
                    default:
                        return null;
                }
            }

            public static com.google.protobuf.Internal.EnumLiteMap<DBOperation>
            internalGetValueMap() {
                return internalValueMap;
            }

            private static final com.google.protobuf.Internal.EnumLiteMap<
                    DBOperation> internalValueMap =
                    new com.google.protobuf.Internal.EnumLiteMap<DBOperation>() {
                        public DBOperation findValueByNumber(int number) {
                            return DBOperation.forNumber(number);
                        }
                    };

            public final com.google.protobuf.Descriptors.EnumValueDescriptor
            getValueDescriptor() {
                return getDescriptor().getValues().get(ordinal());
            }

            public final com.google.protobuf.Descriptors.EnumDescriptor
            getDescriptorForType() {
                return getDescriptor();
            }

            public static final com.google.protobuf.Descriptors.EnumDescriptor
            getDescriptor() {
                return C2S.getDescriptor().getEnumTypes().get(1);
            }

            private static final DBOperation[] VALUES = values();

            public static DBOperation valueOf(
                    com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException(
                            "EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private final int value;

            private DBOperation(int value) {
                this.value = value;
            }

            // @@protoc_insertion_point(enum_scope:io.jopen.snack.common.protol.C2S.DBOperation)
        }

        /**
         * <pre>
         * 表格操作符
         * </pre>
         * <p>
         * Protobuf enum {@code io.jopen.snack.common.protol.C2S.TableOperation}
         */
        public enum TableOperation
                implements com.google.protobuf.ProtocolMessageEnum {
            /**
             * <pre>
             * 创建
             * </pre>
             *
             * <code>createTable = 0;</code>
             */
            createTable(0),
            /**
             * <pre>
             * 删除
             * </pre>
             *
             * <code>dropTable = 1;</code>
             */
            dropTable(1),
            /**
             * <pre>
             * 修改
             * </pre>
             *
             * <code>modifyTable = 2;</code>
             */
            modifyTable(2),
            UNRECOGNIZED(-1),
            ;

            /**
             * <pre>
             * 创建
             * </pre>
             *
             * <code>createTable = 0;</code>
             */
            public static final int createTable_VALUE = 0;
            /**
             * <pre>
             * 删除
             * </pre>
             *
             * <code>dropTable = 1;</code>
             */
            public static final int dropTable_VALUE = 1;
            /**
             * <pre>
             * 修改
             * </pre>
             *
             * <code>modifyTable = 2;</code>
             */
            public static final int modifyTable_VALUE = 2;


            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException(
                            "Can't get the number of an unknown enum value.");
                }
                return value;
            }

            /**
             * @param value The numeric wire value of the corresponding enum entry.
             * @return The enum associated with the given numeric wire value.
             * @deprecated Use {@link #forNumber(int)} instead.
             */
            @Deprecated
            public static TableOperation valueOf(int value) {
                return forNumber(value);
            }

            /**
             * @param value The numeric wire value of the corresponding enum entry.
             * @return The enum associated with the given numeric wire value.
             */
            public static TableOperation forNumber(int value) {
                switch (value) {
                    case 0:
                        return createTable;
                    case 1:
                        return dropTable;
                    case 2:
                        return modifyTable;
                    default:
                        return null;
                }
            }

            public static com.google.protobuf.Internal.EnumLiteMap<TableOperation>
            internalGetValueMap() {
                return internalValueMap;
            }

            private static final com.google.protobuf.Internal.EnumLiteMap<
                    TableOperation> internalValueMap =
                    new com.google.protobuf.Internal.EnumLiteMap<TableOperation>() {
                        public TableOperation findValueByNumber(int number) {
                            return TableOperation.forNumber(number);
                        }
                    };

            public final com.google.protobuf.Descriptors.EnumValueDescriptor
            getValueDescriptor() {
                return getDescriptor().getValues().get(ordinal());
            }

            public final com.google.protobuf.Descriptors.EnumDescriptor
            getDescriptorForType() {
                return getDescriptor();
            }

            public static final com.google.protobuf.Descriptors.EnumDescriptor
            getDescriptor() {
                return C2S.getDescriptor().getEnumTypes().get(2);
            }

            private static final TableOperation[] VALUES = values();

            public static TableOperation valueOf(
                    com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException(
                            "EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private final int value;

            private TableOperation(int value) {
                this.value = value;
            }

            // @@protoc_insertion_point(enum_scope:io.jopen.snack.common.protol.C2S.TableOperation)
        }

        /**
         * <pre>
         * 表格的操作符
         * </pre>
         * <p>
         * Protobuf enum {@code io.jopen.snack.common.protol.C2S.RowOperation}
         */
        public enum RowOperation
                implements com.google.protobuf.ProtocolMessageEnum {
            /**
             * <code>SELECT = 0;</code>
             */
            SELECT(0),
            /**
             * <code>UPDATE = 1;</code>
             */
            UPDATE(1),
            /**
             * <code>INSERT = 2;</code>
             */
            INSERT(2),
            /**
             * <code>DELETE = 4;</code>
             */
            DELETE(4),
            UNRECOGNIZED(-1),
            ;

            /**
             * <code>SELECT = 0;</code>
             */
            public static final int SELECT_VALUE = 0;
            /**
             * <code>UPDATE = 1;</code>
             */
            public static final int UPDATE_VALUE = 1;
            /**
             * <code>INSERT = 2;</code>
             */
            public static final int INSERT_VALUE = 2;
            /**
             * <code>DELETE = 4;</code>
             */
            public static final int DELETE_VALUE = 4;


            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException(
                            "Can't get the number of an unknown enum value.");
                }
                return value;
            }

            /**
             * @param value The numeric wire value of the corresponding enum entry.
             * @return The enum associated with the given numeric wire value.
             * @deprecated Use {@link #forNumber(int)} instead.
             */
            @Deprecated
            public static RowOperation valueOf(int value) {
                return forNumber(value);
            }

            /**
             * @param value The numeric wire value of the corresponding enum entry.
             * @return The enum associated with the given numeric wire value.
             */
            public static RowOperation forNumber(int value) {
                switch (value) {
                    case 0:
                        return SELECT;
                    case 1:
                        return UPDATE;
                    case 2:
                        return INSERT;
                    case 4:
                        return DELETE;
                    default:
                        return null;
                }
            }

            public static com.google.protobuf.Internal.EnumLiteMap<RowOperation>
            internalGetValueMap() {
                return internalValueMap;
            }

            private static final com.google.protobuf.Internal.EnumLiteMap<
                    RowOperation> internalValueMap =
                    new com.google.protobuf.Internal.EnumLiteMap<RowOperation>() {
                        public RowOperation findValueByNumber(int number) {
                            return RowOperation.forNumber(number);
                        }
                    };

            public final com.google.protobuf.Descriptors.EnumValueDescriptor
            getValueDescriptor() {
                return getDescriptor().getValues().get(ordinal());
            }

            public final com.google.protobuf.Descriptors.EnumDescriptor
            getDescriptorForType() {
                return getDescriptor();
            }

            public static final com.google.protobuf.Descriptors.EnumDescriptor
            getDescriptor() {
                return C2S.getDescriptor().getEnumTypes().get(3);
            }

            private static final RowOperation[] VALUES = values();

            public static RowOperation valueOf(
                    com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException(
                            "EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private final int value;

            private RowOperation(int value) {
                this.value = value;
            }

            // @@protoc_insertion_point(enum_scope:io.jopen.snack.common.protol.C2S.RowOperation)
        }

        public static final int CONDITIONS_FIELD_NUMBER = 1;
        private java.util.List<com.google.protobuf.Any> conditions_;

        /**
         * <pre>
         * 条件体
         * </pre>
         *
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        public java.util.List<com.google.protobuf.Any> getConditionsList() {
            return conditions_;
        }

        /**
         * <pre>
         * 条件体
         * </pre>
         *
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        public java.util.List<? extends com.google.protobuf.AnyOrBuilder>
        getConditionsOrBuilderList() {
            return conditions_;
        }

        /**
         * <pre>
         * 条件体
         * </pre>
         *
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        public int getConditionsCount() {
            return conditions_.size();
        }

        /**
         * <pre>
         * 条件体
         * </pre>
         *
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        public com.google.protobuf.Any getConditions(int index) {
            return conditions_.get(index);
        }

        /**
         * <pre>
         * 条件体
         * </pre>
         *
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        public com.google.protobuf.AnyOrBuilder getConditionsOrBuilder(
                int index) {
            return conditions_.get(index);
        }

        public static final int ROWS_FIELD_NUMBER = 2;
        private java.util.List<com.google.protobuf.Any> rows_;

        /**
         * <pre>
         * 实体对象
         * </pre>
         *
         * <code>repeated .google.protobuf.Any rows = 2;</code>
         */
        public java.util.List<com.google.protobuf.Any> getRowsList() {
            return rows_;
        }

        /**
         * <pre>
         * 实体对象
         * </pre>
         *
         * <code>repeated .google.protobuf.Any rows = 2;</code>
         */
        public java.util.List<? extends com.google.protobuf.AnyOrBuilder>
        getRowsOrBuilderList() {
            return rows_;
        }

        /**
         * <pre>
         * 实体对象
         * </pre>
         *
         * <code>repeated .google.protobuf.Any rows = 2;</code>
         */
        public int getRowsCount() {
            return rows_.size();
        }

        /**
         * <pre>
         * 实体对象
         * </pre>
         *
         * <code>repeated .google.protobuf.Any rows = 2;</code>
         */
        public com.google.protobuf.Any getRows(int index) {
            return rows_.get(index);
        }

        /**
         * <pre>
         * 实体对象
         * </pre>
         *
         * <code>repeated .google.protobuf.Any rows = 2;</code>
         */
        public com.google.protobuf.AnyOrBuilder getRowsOrBuilder(
                int index) {
            return rows_.get(index);
        }

        public static final int OPERATIONLEVEL_FIELD_NUMBER = 3;
        private int operationLevel_;

        /**
         * <pre>
         * 操作级别
         * </pre>
         *
         * <code>.io.jopen.snack.common.protol.C2S.OperationLevel operationLevel = 3;</code>
         *
         * @return The enum numeric value on the wire for operationLevel.
         */
        public int getOperationLevelValue() {
            return operationLevel_;
        }

        /**
         * <pre>
         * 操作级别
         * </pre>
         *
         * <code>.io.jopen.snack.common.protol.C2S.OperationLevel operationLevel = 3;</code>
         *
         * @return The operationLevel.
         */
        public OperationLevel getOperationLevel() {
            @SuppressWarnings("deprecation")
            OperationLevel result = OperationLevel.valueOf(operationLevel_);
            return result == null ? OperationLevel.UNRECOGNIZED : result;
        }

        public static final int DBOPERATION_FIELD_NUMBER = 4;
        private int dbOperation_;

        /**
         * <pre>
         * 数据库操作符
         * </pre>
         *
         * <code>.io.jopen.snack.common.protol.C2S.DBOperation dbOperation = 4;</code>
         *
         * @return The enum numeric value on the wire for dbOperation.
         */
        public int getDbOperationValue() {
            return dbOperation_;
        }

        /**
         * <pre>
         * 数据库操作符
         * </pre>
         *
         * <code>.io.jopen.snack.common.protol.C2S.DBOperation dbOperation = 4;</code>
         *
         * @return The dbOperation.
         */
        public DBOperation getDbOperation() {
            @SuppressWarnings("deprecation")
            DBOperation result = DBOperation.valueOf(dbOperation_);
            return result == null ? DBOperation.UNRECOGNIZED : result;
        }

        public static final int TABLEOPERATION_FIELD_NUMBER = 5;
        private int tableOperation_;

        /**
         * <pre>
         * 表格操作符
         * </pre>
         *
         * <code>.io.jopen.snack.common.protol.C2S.TableOperation tableOperation = 5;</code>
         *
         * @return The enum numeric value on the wire for tableOperation.
         */
        public int getTableOperationValue() {
            return tableOperation_;
        }

        /**
         * <pre>
         * 表格操作符
         * </pre>
         *
         * <code>.io.jopen.snack.common.protol.C2S.TableOperation tableOperation = 5;</code>
         *
         * @return The tableOperation.
         */
        public TableOperation getTableOperation() {
            @SuppressWarnings("deprecation")
            TableOperation result = TableOperation.valueOf(tableOperation_);
            return result == null ? TableOperation.UNRECOGNIZED : result;
        }

        public static final int ROWOPTION_FIELD_NUMBER = 6;
        private int rowOption_;

        /**
         * <pre>
         * 行操作符
         * </pre>
         *
         * <code>.io.jopen.snack.common.protol.C2S.RowOperation rowOption = 6;</code>
         *
         * @return The enum numeric value on the wire for rowOption.
         */
        public int getRowOptionValue() {
            return rowOption_;
        }

        /**
         * <pre>
         * 行操作符
         * </pre>
         *
         * <code>.io.jopen.snack.common.protol.C2S.RowOperation rowOption = 6;</code>
         *
         * @return The rowOption.
         */
        public RowOperation getRowOption() {
            @SuppressWarnings("deprecation")
            RowOperation result = RowOperation.valueOf(rowOption_);
            return result == null ? RowOperation.UNRECOGNIZED : result;
        }

        private byte memoizedIsInitialized = -1;

        @Override
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized == 1) return true;
            if (isInitialized == 0) return false;

            memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            for (int i = 0; i < conditions_.size(); i++) {
                output.writeMessage(1, conditions_.get(i));
            }
            for (int i = 0; i < rows_.size(); i++) {
                output.writeMessage(2, rows_.get(i));
            }
            if (operationLevel_ != OperationLevel.database.getNumber()) {
                output.writeEnum(3, operationLevel_);
            }
            if (dbOperation_ != DBOperation.createDB.getNumber()) {
                output.writeEnum(4, dbOperation_);
            }
            if (tableOperation_ != TableOperation.createTable.getNumber()) {
                output.writeEnum(5, tableOperation_);
            }
            if (rowOption_ != RowOperation.SELECT.getNumber()) {
                output.writeEnum(6, rowOption_);
            }
            unknownFields.writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = memoizedSize;
            if (size != -1) return size;

            size = 0;
            for (int i = 0; i < conditions_.size(); i++) {
                size += com.google.protobuf.CodedOutputStream
                        .computeMessageSize(1, conditions_.get(i));
            }
            for (int i = 0; i < rows_.size(); i++) {
                size += com.google.protobuf.CodedOutputStream
                        .computeMessageSize(2, rows_.get(i));
            }
            if (operationLevel_ != OperationLevel.database.getNumber()) {
                size += com.google.protobuf.CodedOutputStream
                        .computeEnumSize(3, operationLevel_);
            }
            if (dbOperation_ != DBOperation.createDB.getNumber()) {
                size += com.google.protobuf.CodedOutputStream
                        .computeEnumSize(4, dbOperation_);
            }
            if (tableOperation_ != TableOperation.createTable.getNumber()) {
                size += com.google.protobuf.CodedOutputStream
                        .computeEnumSize(5, tableOperation_);
            }
            if (rowOption_ != RowOperation.SELECT.getNumber()) {
                size += com.google.protobuf.CodedOutputStream
                        .computeEnumSize(6, rowOption_);
            }
            size += unknownFields.getSerializedSize();
            memoizedSize = size;
            return size;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof C2S)) {
                return super.equals(obj);
            }
            C2S other = (C2S) obj;

            if (!getConditionsList()
                    .equals(other.getConditionsList())) return false;
            if (!getRowsList()
                    .equals(other.getRowsList())) return false;
            if (operationLevel_ != other.operationLevel_) return false;
            if (dbOperation_ != other.dbOperation_) return false;
            if (tableOperation_ != other.tableOperation_) return false;
            if (rowOption_ != other.rowOption_) return false;
            if (!unknownFields.equals(other.unknownFields)) return false;
            return true;
        }

        @Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptor().hashCode();
            if (getConditionsCount() > 0) {
                hash = (37 * hash) + CONDITIONS_FIELD_NUMBER;
                hash = (53 * hash) + getConditionsList().hashCode();
            }
            if (getRowsCount() > 0) {
                hash = (37 * hash) + ROWS_FIELD_NUMBER;
                hash = (53 * hash) + getRowsList().hashCode();
            }
            hash = (37 * hash) + OPERATIONLEVEL_FIELD_NUMBER;
            hash = (53 * hash) + operationLevel_;
            hash = (37 * hash) + DBOPERATION_FIELD_NUMBER;
            hash = (53 * hash) + dbOperation_;
            hash = (37 * hash) + TABLEOPERATION_FIELD_NUMBER;
            hash = (53 * hash) + tableOperation_;
            hash = (37 * hash) + ROWOPTION_FIELD_NUMBER;
            hash = (53 * hash) + rowOption_;
            hash = (29 * hash) + unknownFields.hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static C2S parseFrom(
                java.nio.ByteBuffer data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static C2S parseFrom(
                java.nio.ByteBuffer data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static C2S parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static C2S parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static C2S parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static C2S parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static C2S parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static C2S parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static C2S parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input);
        }

        public static C2S parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static C2S parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static C2S parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(C2S prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE
                    ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(
                BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        /**
         * Protobuf type {@code io.jopen.snack.common.protol.C2S}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
                // @@protoc_insertion_point(builder_implements:io.jopen.snack.common.protol.C2S)
                C2SOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return RpcData.internal_static_io_jopen_snack_common_protol_C2S_descriptor;
            }

            @Override
            protected FieldAccessorTable
            internalGetFieldAccessorTable() {
                return RpcData.internal_static_io_jopen_snack_common_protol_C2S_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                C2S.class, Builder.class);
            }

            // Construct using io.jopen.snack.common.protol.RpcData.C2S.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessageV3
                        .alwaysUseFieldBuilders) {
                    getConditionsFieldBuilder();
                    getRowsFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (conditionsBuilder_ == null) {
                    conditions_ = java.util.Collections.emptyList();
                    bitField0_ = (bitField0_ & ~0x00000001);
                } else {
                    conditionsBuilder_.clear();
                }
                if (rowsBuilder_ == null) {
                    rows_ = java.util.Collections.emptyList();
                    bitField0_ = (bitField0_ & ~0x00000002);
                } else {
                    rowsBuilder_.clear();
                }
                operationLevel_ = 0;

                dbOperation_ = 0;

                tableOperation_ = 0;

                rowOption_ = 0;

                return this;
            }

            @Override
            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return RpcData.internal_static_io_jopen_snack_common_protol_C2S_descriptor;
            }

            @Override
            public C2S getDefaultInstanceForType() {
                return C2S.getDefaultInstance();
            }

            @Override
            public C2S build() {
                C2S result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public C2S buildPartial() {
                C2S result = new C2S(this);
                int from_bitField0_ = bitField0_;
                if (conditionsBuilder_ == null) {
                    if (((bitField0_ & 0x00000001) != 0)) {
                        conditions_ = java.util.Collections.unmodifiableList(conditions_);
                        bitField0_ = (bitField0_ & ~0x00000001);
                    }
                    result.conditions_ = conditions_;
                } else {
                    result.conditions_ = conditionsBuilder_.build();
                }
                if (rowsBuilder_ == null) {
                    if (((bitField0_ & 0x00000002) != 0)) {
                        rows_ = java.util.Collections.unmodifiableList(rows_);
                        bitField0_ = (bitField0_ & ~0x00000002);
                    }
                    result.rows_ = rows_;
                } else {
                    result.rows_ = rowsBuilder_.build();
                }
                result.operationLevel_ = operationLevel_;
                result.dbOperation_ = dbOperation_;
                result.tableOperation_ = tableOperation_;
                result.rowOption_ = rowOption_;
                onBuilt();
                return result;
            }

            @Override
            public Builder clone() {
                return super.clone();
            }

            @Override
            public Builder setField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return super.setField(field, value);
            }

            @Override
            public Builder clearField(
                    com.google.protobuf.Descriptors.FieldDescriptor field) {
                return super.clearField(field);
            }

            @Override
            public Builder clearOneof(
                    com.google.protobuf.Descriptors.OneofDescriptor oneof) {
                return super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    int index, Object value) {
                return super.setRepeatedField(field, index, value);
            }

            @Override
            public Builder addRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return super.addRepeatedField(field, value);
            }

            @Override
            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof C2S) {
                    return mergeFrom((C2S) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(C2S other) {
                if (other == C2S.getDefaultInstance()) return this;
                if (conditionsBuilder_ == null) {
                    if (!other.conditions_.isEmpty()) {
                        if (conditions_.isEmpty()) {
                            conditions_ = other.conditions_;
                            bitField0_ = (bitField0_ & ~0x00000001);
                        } else {
                            ensureConditionsIsMutable();
                            conditions_.addAll(other.conditions_);
                        }
                        onChanged();
                    }
                } else {
                    if (!other.conditions_.isEmpty()) {
                        if (conditionsBuilder_.isEmpty()) {
                            conditionsBuilder_.dispose();
                            conditionsBuilder_ = null;
                            conditions_ = other.conditions_;
                            bitField0_ = (bitField0_ & ~0x00000001);
                            conditionsBuilder_ =
                                    com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                                            getConditionsFieldBuilder() : null;
                        } else {
                            conditionsBuilder_.addAllMessages(other.conditions_);
                        }
                    }
                }
                if (rowsBuilder_ == null) {
                    if (!other.rows_.isEmpty()) {
                        if (rows_.isEmpty()) {
                            rows_ = other.rows_;
                            bitField0_ = (bitField0_ & ~0x00000002);
                        } else {
                            ensureRowsIsMutable();
                            rows_.addAll(other.rows_);
                        }
                        onChanged();
                    }
                } else {
                    if (!other.rows_.isEmpty()) {
                        if (rowsBuilder_.isEmpty()) {
                            rowsBuilder_.dispose();
                            rowsBuilder_ = null;
                            rows_ = other.rows_;
                            bitField0_ = (bitField0_ & ~0x00000002);
                            rowsBuilder_ =
                                    com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                                            getRowsFieldBuilder() : null;
                        } else {
                            rowsBuilder_.addAllMessages(other.rows_);
                        }
                    }
                }
                if (other.operationLevel_ != 0) {
                    setOperationLevelValue(other.getOperationLevelValue());
                }
                if (other.dbOperation_ != 0) {
                    setDbOperationValue(other.getDbOperationValue());
                }
                if (other.tableOperation_ != 0) {
                    setTableOperationValue(other.getTableOperationValue());
                }
                if (other.rowOption_ != 0) {
                    setRowOptionValue(other.getRowOptionValue());
                }
                this.mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                C2S parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (C2S) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private int bitField0_;

            private java.util.List<com.google.protobuf.Any> conditions_ =
                    java.util.Collections.emptyList();

            private void ensureConditionsIsMutable() {
                if (!((bitField0_ & 0x00000001) != 0)) {
                    conditions_ = new java.util.ArrayList<com.google.protobuf.Any>(conditions_);
                    bitField0_ |= 0x00000001;
                }
            }

            private com.google.protobuf.RepeatedFieldBuilderV3<
                    com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder> conditionsBuilder_;

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public java.util.List<com.google.protobuf.Any> getConditionsList() {
                if (conditionsBuilder_ == null) {
                    return java.util.Collections.unmodifiableList(conditions_);
                } else {
                    return conditionsBuilder_.getMessageList();
                }
            }

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public int getConditionsCount() {
                if (conditionsBuilder_ == null) {
                    return conditions_.size();
                } else {
                    return conditionsBuilder_.getCount();
                }
            }

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public com.google.protobuf.Any getConditions(int index) {
                if (conditionsBuilder_ == null) {
                    return conditions_.get(index);
                } else {
                    return conditionsBuilder_.getMessage(index);
                }
            }

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder setConditions(
                    int index, com.google.protobuf.Any value) {
                if (conditionsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureConditionsIsMutable();
                    conditions_.set(index, value);
                    onChanged();
                } else {
                    conditionsBuilder_.setMessage(index, value);
                }
                return this;
            }

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder setConditions(
                    int index, com.google.protobuf.Any.Builder builderForValue) {
                if (conditionsBuilder_ == null) {
                    ensureConditionsIsMutable();
                    conditions_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    conditionsBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder addConditions(com.google.protobuf.Any value) {
                if (conditionsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureConditionsIsMutable();
                    conditions_.add(value);
                    onChanged();
                } else {
                    conditionsBuilder_.addMessage(value);
                }
                return this;
            }

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder addConditions(
                    int index, com.google.protobuf.Any value) {
                if (conditionsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureConditionsIsMutable();
                    conditions_.add(index, value);
                    onChanged();
                } else {
                    conditionsBuilder_.addMessage(index, value);
                }
                return this;
            }

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder addConditions(
                    com.google.protobuf.Any.Builder builderForValue) {
                if (conditionsBuilder_ == null) {
                    ensureConditionsIsMutable();
                    conditions_.add(builderForValue.build());
                    onChanged();
                } else {
                    conditionsBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder addConditions(
                    int index, com.google.protobuf.Any.Builder builderForValue) {
                if (conditionsBuilder_ == null) {
                    ensureConditionsIsMutable();
                    conditions_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    conditionsBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder addAllConditions(
                    Iterable<? extends com.google.protobuf.Any> values) {
                if (conditionsBuilder_ == null) {
                    ensureConditionsIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(
                            values, conditions_);
                    onChanged();
                } else {
                    conditionsBuilder_.addAllMessages(values);
                }
                return this;
            }

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder clearConditions() {
                if (conditionsBuilder_ == null) {
                    conditions_ = java.util.Collections.emptyList();
                    bitField0_ = (bitField0_ & ~0x00000001);
                    onChanged();
                } else {
                    conditionsBuilder_.clear();
                }
                return this;
            }

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder removeConditions(int index) {
                if (conditionsBuilder_ == null) {
                    ensureConditionsIsMutable();
                    conditions_.remove(index);
                    onChanged();
                } else {
                    conditionsBuilder_.remove(index);
                }
                return this;
            }

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public com.google.protobuf.Any.Builder getConditionsBuilder(
                    int index) {
                return getConditionsFieldBuilder().getBuilder(index);
            }

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public com.google.protobuf.AnyOrBuilder getConditionsOrBuilder(
                    int index) {
                if (conditionsBuilder_ == null) {
                    return conditions_.get(index);
                } else {
                    return conditionsBuilder_.getMessageOrBuilder(index);
                }
            }

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public java.util.List<? extends com.google.protobuf.AnyOrBuilder>
            getConditionsOrBuilderList() {
                if (conditionsBuilder_ != null) {
                    return conditionsBuilder_.getMessageOrBuilderList();
                } else {
                    return java.util.Collections.unmodifiableList(conditions_);
                }
            }

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public com.google.protobuf.Any.Builder addConditionsBuilder() {
                return getConditionsFieldBuilder().addBuilder(
                        com.google.protobuf.Any.getDefaultInstance());
            }

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public com.google.protobuf.Any.Builder addConditionsBuilder(
                    int index) {
                return getConditionsFieldBuilder().addBuilder(
                        index, com.google.protobuf.Any.getDefaultInstance());
            }

            /**
             * <pre>
             * 条件体
             * </pre>
             *
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public java.util.List<com.google.protobuf.Any.Builder>
            getConditionsBuilderList() {
                return getConditionsFieldBuilder().getBuilderList();
            }

            private com.google.protobuf.RepeatedFieldBuilderV3<
                    com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder>
            getConditionsFieldBuilder() {
                if (conditionsBuilder_ == null) {
                    conditionsBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
                            com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder>(
                            conditions_,
                            ((bitField0_ & 0x00000001) != 0),
                            getParentForChildren(),
                            isClean());
                    conditions_ = null;
                }
                return conditionsBuilder_;
            }

            private java.util.List<com.google.protobuf.Any> rows_ =
                    java.util.Collections.emptyList();

            private void ensureRowsIsMutable() {
                if (!((bitField0_ & 0x00000002) != 0)) {
                    rows_ = new java.util.ArrayList<com.google.protobuf.Any>(rows_);
                    bitField0_ |= 0x00000002;
                }
            }

            private com.google.protobuf.RepeatedFieldBuilderV3<
                    com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder> rowsBuilder_;

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public java.util.List<com.google.protobuf.Any> getRowsList() {
                if (rowsBuilder_ == null) {
                    return java.util.Collections.unmodifiableList(rows_);
                } else {
                    return rowsBuilder_.getMessageList();
                }
            }

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public int getRowsCount() {
                if (rowsBuilder_ == null) {
                    return rows_.size();
                } else {
                    return rowsBuilder_.getCount();
                }
            }

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public com.google.protobuf.Any getRows(int index) {
                if (rowsBuilder_ == null) {
                    return rows_.get(index);
                } else {
                    return rowsBuilder_.getMessage(index);
                }
            }

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public Builder setRows(
                    int index, com.google.protobuf.Any value) {
                if (rowsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureRowsIsMutable();
                    rows_.set(index, value);
                    onChanged();
                } else {
                    rowsBuilder_.setMessage(index, value);
                }
                return this;
            }

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public Builder setRows(
                    int index, com.google.protobuf.Any.Builder builderForValue) {
                if (rowsBuilder_ == null) {
                    ensureRowsIsMutable();
                    rows_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    rowsBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public Builder addRows(com.google.protobuf.Any value) {
                if (rowsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureRowsIsMutable();
                    rows_.add(value);
                    onChanged();
                } else {
                    rowsBuilder_.addMessage(value);
                }
                return this;
            }

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public Builder addRows(
                    int index, com.google.protobuf.Any value) {
                if (rowsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureRowsIsMutable();
                    rows_.add(index, value);
                    onChanged();
                } else {
                    rowsBuilder_.addMessage(index, value);
                }
                return this;
            }

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public Builder addRows(
                    com.google.protobuf.Any.Builder builderForValue) {
                if (rowsBuilder_ == null) {
                    ensureRowsIsMutable();
                    rows_.add(builderForValue.build());
                    onChanged();
                } else {
                    rowsBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public Builder addRows(
                    int index, com.google.protobuf.Any.Builder builderForValue) {
                if (rowsBuilder_ == null) {
                    ensureRowsIsMutable();
                    rows_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    rowsBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public Builder addAllRows(
                    Iterable<? extends com.google.protobuf.Any> values) {
                if (rowsBuilder_ == null) {
                    ensureRowsIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(
                            values, rows_);
                    onChanged();
                } else {
                    rowsBuilder_.addAllMessages(values);
                }
                return this;
            }

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public Builder clearRows() {
                if (rowsBuilder_ == null) {
                    rows_ = java.util.Collections.emptyList();
                    bitField0_ = (bitField0_ & ~0x00000002);
                    onChanged();
                } else {
                    rowsBuilder_.clear();
                }
                return this;
            }

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public Builder removeRows(int index) {
                if (rowsBuilder_ == null) {
                    ensureRowsIsMutable();
                    rows_.remove(index);
                    onChanged();
                } else {
                    rowsBuilder_.remove(index);
                }
                return this;
            }

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public com.google.protobuf.Any.Builder getRowsBuilder(
                    int index) {
                return getRowsFieldBuilder().getBuilder(index);
            }

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public com.google.protobuf.AnyOrBuilder getRowsOrBuilder(
                    int index) {
                if (rowsBuilder_ == null) {
                    return rows_.get(index);
                } else {
                    return rowsBuilder_.getMessageOrBuilder(index);
                }
            }

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public java.util.List<? extends com.google.protobuf.AnyOrBuilder>
            getRowsOrBuilderList() {
                if (rowsBuilder_ != null) {
                    return rowsBuilder_.getMessageOrBuilderList();
                } else {
                    return java.util.Collections.unmodifiableList(rows_);
                }
            }

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public com.google.protobuf.Any.Builder addRowsBuilder() {
                return getRowsFieldBuilder().addBuilder(
                        com.google.protobuf.Any.getDefaultInstance());
            }

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public com.google.protobuf.Any.Builder addRowsBuilder(
                    int index) {
                return getRowsFieldBuilder().addBuilder(
                        index, com.google.protobuf.Any.getDefaultInstance());
            }

            /**
             * <pre>
             * 实体对象
             * </pre>
             *
             * <code>repeated .google.protobuf.Any rows = 2;</code>
             */
            public java.util.List<com.google.protobuf.Any.Builder>
            getRowsBuilderList() {
                return getRowsFieldBuilder().getBuilderList();
            }

            private com.google.protobuf.RepeatedFieldBuilderV3<
                    com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder>
            getRowsFieldBuilder() {
                if (rowsBuilder_ == null) {
                    rowsBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
                            com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder>(
                            rows_,
                            ((bitField0_ & 0x00000002) != 0),
                            getParentForChildren(),
                            isClean());
                    rows_ = null;
                }
                return rowsBuilder_;
            }

            private int operationLevel_ = 0;

            /**
             * <pre>
             * 操作级别
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.OperationLevel operationLevel = 3;</code>
             *
             * @return The enum numeric value on the wire for operationLevel.
             */
            public int getOperationLevelValue() {
                return operationLevel_;
            }

            /**
             * <pre>
             * 操作级别
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.OperationLevel operationLevel = 3;</code>
             *
             * @param value The enum numeric value on the wire for operationLevel to set.
             * @return This builder for chaining.
             */
            public Builder setOperationLevelValue(int value) {
                operationLevel_ = value;
                onChanged();
                return this;
            }

            /**
             * <pre>
             * 操作级别
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.OperationLevel operationLevel = 3;</code>
             *
             * @return The operationLevel.
             */
            public OperationLevel getOperationLevel() {
                @SuppressWarnings("deprecation")
                OperationLevel result = OperationLevel.valueOf(operationLevel_);
                return result == null ? OperationLevel.UNRECOGNIZED : result;
            }

            /**
             * <pre>
             * 操作级别
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.OperationLevel operationLevel = 3;</code>
             *
             * @param value The operationLevel to set.
             * @return This builder for chaining.
             */
            public Builder setOperationLevel(OperationLevel value) {
                if (value == null) {
                    throw new NullPointerException();
                }

                operationLevel_ = value.getNumber();
                onChanged();
                return this;
            }

            /**
             * <pre>
             * 操作级别
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.OperationLevel operationLevel = 3;</code>
             *
             * @return This builder for chaining.
             */
            public Builder clearOperationLevel() {

                operationLevel_ = 0;
                onChanged();
                return this;
            }

            private int dbOperation_ = 0;

            /**
             * <pre>
             * 数据库操作符
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.DBOperation dbOperation = 4;</code>
             *
             * @return The enum numeric value on the wire for dbOperation.
             */
            public int getDbOperationValue() {
                return dbOperation_;
            }

            /**
             * <pre>
             * 数据库操作符
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.DBOperation dbOperation = 4;</code>
             *
             * @param value The enum numeric value on the wire for dbOperation to set.
             * @return This builder for chaining.
             */
            public Builder setDbOperationValue(int value) {
                dbOperation_ = value;
                onChanged();
                return this;
            }

            /**
             * <pre>
             * 数据库操作符
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.DBOperation dbOperation = 4;</code>
             *
             * @return The dbOperation.
             */
            public DBOperation getDbOperation() {
                @SuppressWarnings("deprecation")
                DBOperation result = DBOperation.valueOf(dbOperation_);
                return result == null ? DBOperation.UNRECOGNIZED : result;
            }

            /**
             * <pre>
             * 数据库操作符
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.DBOperation dbOperation = 4;</code>
             *
             * @param value The dbOperation to set.
             * @return This builder for chaining.
             */
            public Builder setDbOperation(DBOperation value) {
                if (value == null) {
                    throw new NullPointerException();
                }

                dbOperation_ = value.getNumber();
                onChanged();
                return this;
            }

            /**
             * <pre>
             * 数据库操作符
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.DBOperation dbOperation = 4;</code>
             *
             * @return This builder for chaining.
             */
            public Builder clearDbOperation() {

                dbOperation_ = 0;
                onChanged();
                return this;
            }

            private int tableOperation_ = 0;

            /**
             * <pre>
             * 表格操作符
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.TableOperation tableOperation = 5;</code>
             *
             * @return The enum numeric value on the wire for tableOperation.
             */
            public int getTableOperationValue() {
                return tableOperation_;
            }

            /**
             * <pre>
             * 表格操作符
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.TableOperation tableOperation = 5;</code>
             *
             * @param value The enum numeric value on the wire for tableOperation to set.
             * @return This builder for chaining.
             */
            public Builder setTableOperationValue(int value) {
                tableOperation_ = value;
                onChanged();
                return this;
            }

            /**
             * <pre>
             * 表格操作符
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.TableOperation tableOperation = 5;</code>
             *
             * @return The tableOperation.
             */
            public TableOperation getTableOperation() {
                @SuppressWarnings("deprecation")
                TableOperation result = TableOperation.valueOf(tableOperation_);
                return result == null ? TableOperation.UNRECOGNIZED : result;
            }

            /**
             * <pre>
             * 表格操作符
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.TableOperation tableOperation = 5;</code>
             *
             * @param value The tableOperation to set.
             * @return This builder for chaining.
             */
            public Builder setTableOperation(TableOperation value) {
                if (value == null) {
                    throw new NullPointerException();
                }

                tableOperation_ = value.getNumber();
                onChanged();
                return this;
            }

            /**
             * <pre>
             * 表格操作符
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.TableOperation tableOperation = 5;</code>
             *
             * @return This builder for chaining.
             */
            public Builder clearTableOperation() {

                tableOperation_ = 0;
                onChanged();
                return this;
            }

            private int rowOption_ = 0;

            /**
             * <pre>
             * 行操作符
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.RowOperation rowOption = 6;</code>
             *
             * @return The enum numeric value on the wire for rowOption.
             */
            public int getRowOptionValue() {
                return rowOption_;
            }

            /**
             * <pre>
             * 行操作符
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.RowOperation rowOption = 6;</code>
             *
             * @param value The enum numeric value on the wire for rowOption to set.
             * @return This builder for chaining.
             */
            public Builder setRowOptionValue(int value) {
                rowOption_ = value;
                onChanged();
                return this;
            }

            /**
             * <pre>
             * 行操作符
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.RowOperation rowOption = 6;</code>
             *
             * @return The rowOption.
             */
            public RowOperation getRowOption() {
                @SuppressWarnings("deprecation")
                RowOperation result = RowOperation.valueOf(rowOption_);
                return result == null ? RowOperation.UNRECOGNIZED : result;
            }

            /**
             * <pre>
             * 行操作符
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.RowOperation rowOption = 6;</code>
             *
             * @param value The rowOption to set.
             * @return This builder for chaining.
             */
            public Builder setRowOption(RowOperation value) {
                if (value == null) {
                    throw new NullPointerException();
                }

                rowOption_ = value.getNumber();
                onChanged();
                return this;
            }

            /**
             * <pre>
             * 行操作符
             * </pre>
             *
             * <code>.io.jopen.snack.common.protol.C2S.RowOperation rowOption = 6;</code>
             *
             * @return This builder for chaining.
             */
            public Builder clearRowOption() {

                rowOption_ = 0;
                onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.mergeUnknownFields(unknownFields);
            }


            // @@protoc_insertion_point(builder_scope:io.jopen.snack.common.protol.C2S)
        }

        // @@protoc_insertion_point(class_scope:io.jopen.snack.common.protol.C2S)
        private static final C2S DEFAULT_INSTANCE;

        static {
            DEFAULT_INSTANCE = new C2S();
        }

        public static C2S getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        private static final com.google.protobuf.Parser<C2S>
                PARSER = new com.google.protobuf.AbstractParser<C2S>() {
            @Override
            public C2S parsePartialFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return new C2S(input, extensionRegistry);
            }
        };

        public static com.google.protobuf.Parser<C2S> parser() {
            return PARSER;
        }

        @Override
        public com.google.protobuf.Parser<C2S> getParserForType() {
            return PARSER;
        }

        @Override
        public C2S getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

    }

    public interface S2COrBuilder extends
            // @@protoc_insertion_point(interface_extends:io.jopen.snack.common.protol.S2C)
            com.google.protobuf.MessageOrBuilder {

        /**
         * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
         */
        java.util.List<com.google.protobuf.Any>
        getCollectionResList();

        /**
         * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
         */
        com.google.protobuf.Any getCollectionRes(int index);

        /**
         * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
         */
        int getCollectionResCount();

        /**
         * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
         */
        java.util.List<? extends com.google.protobuf.AnyOrBuilder>
        getCollectionResOrBuilderList();

        /**
         * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
         */
        com.google.protobuf.AnyOrBuilder getCollectionResOrBuilder(
                int index);

        /**
         * <code>int32 updateRow = 8;</code>
         *
         * @return The updateRow.
         */
        int getUpdateRow();

        /**
         * <code>string errMsg = 9;</code>
         *
         * @return The errMsg.
         */
        String getErrMsg();

        /**
         * <code>string errMsg = 9;</code>
         *
         * @return The bytes for errMsg.
         */
        com.google.protobuf.ByteString
        getErrMsgBytes();

        /**
         * <code>int32 code = 10;</code>
         *
         * @return The code.
         */
        int getCode();
    }

    /**
     * Protobuf type {@code io.jopen.snack.common.protol.S2C}
     */
    public static final class S2C extends
            com.google.protobuf.GeneratedMessageV3 implements
            // @@protoc_insertion_point(message_implements:io.jopen.snack.common.protol.S2C)
            S2COrBuilder {
        private static final long serialVersionUID = 0L;

        // Use S2C.newBuilder() to construct.
        private S2C(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private S2C() {
            collectionRes_ = java.util.Collections.emptyList();
            errMsg_ = "";
        }

        @Override
        @SuppressWarnings({"unused"})
        protected Object newInstance(
                UnusedPrivateParameter unused) {
            return new S2C();
        }

        @Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }

        private S2C(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 58: {
                            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                                collectionRes_ = new java.util.ArrayList<com.google.protobuf.Any>();
                                mutable_bitField0_ |= 0x00000001;
                            }
                            collectionRes_.add(
                                    input.readMessage(com.google.protobuf.Any.parser(), extensionRegistry));
                            break;
                        }
                        case 64: {

                            updateRow_ = input.readInt32();
                            break;
                        }
                        case 74: {
                            String s = input.readStringRequireUtf8();

                            errMsg_ = s;
                            break;
                        }
                        case 80: {

                            code_ = input.readInt32();
                            break;
                        }
                        default: {
                            if (!parseUnknownField(
                                    input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e).setUnfinishedMessage(this);
            } finally {
                if (((mutable_bitField0_ & 0x00000001) != 0)) {
                    collectionRes_ = java.util.Collections.unmodifiableList(collectionRes_);
                }
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }

        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return RpcData.internal_static_io_jopen_snack_common_protol_S2C_descriptor;
        }

        @Override
        protected FieldAccessorTable
        internalGetFieldAccessorTable() {
            return RpcData.internal_static_io_jopen_snack_common_protol_S2C_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            S2C.class, Builder.class);
        }

        public static final int COLLECTIONRES_FIELD_NUMBER = 7;
        private java.util.List<com.google.protobuf.Any> collectionRes_;

        /**
         * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
         */
        public java.util.List<com.google.protobuf.Any> getCollectionResList() {
            return collectionRes_;
        }

        /**
         * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
         */
        public java.util.List<? extends com.google.protobuf.AnyOrBuilder>
        getCollectionResOrBuilderList() {
            return collectionRes_;
        }

        /**
         * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
         */
        public int getCollectionResCount() {
            return collectionRes_.size();
        }

        /**
         * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
         */
        public com.google.protobuf.Any getCollectionRes(int index) {
            return collectionRes_.get(index);
        }

        /**
         * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
         */
        public com.google.protobuf.AnyOrBuilder getCollectionResOrBuilder(
                int index) {
            return collectionRes_.get(index);
        }

        public static final int UPDATEROW_FIELD_NUMBER = 8;
        private int updateRow_;

        /**
         * <code>int32 updateRow = 8;</code>
         *
         * @return The updateRow.
         */
        public int getUpdateRow() {
            return updateRow_;
        }

        public static final int ERRMSG_FIELD_NUMBER = 9;
        private volatile Object errMsg_;

        /**
         * <code>string errMsg = 9;</code>
         *
         * @return The errMsg.
         */
        public String getErrMsg() {
            Object ref = errMsg_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                String s = bs.toStringUtf8();
                errMsg_ = s;
                return s;
            }
        }

        /**
         * <code>string errMsg = 9;</code>
         *
         * @return The bytes for errMsg.
         */
        public com.google.protobuf.ByteString
        getErrMsgBytes() {
            Object ref = errMsg_;
            if (ref instanceof String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (String) ref);
                errMsg_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        public static final int CODE_FIELD_NUMBER = 10;
        private int code_;

        /**
         * <code>int32 code = 10;</code>
         *
         * @return The code.
         */
        public int getCode() {
            return code_;
        }

        private byte memoizedIsInitialized = -1;

        @Override
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized == 1) return true;
            if (isInitialized == 0) return false;

            memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            for (int i = 0; i < collectionRes_.size(); i++) {
                output.writeMessage(7, collectionRes_.get(i));
            }
            if (updateRow_ != 0) {
                output.writeInt32(8, updateRow_);
            }
            if (!getErrMsgBytes().isEmpty()) {
                com.google.protobuf.GeneratedMessageV3.writeString(output, 9, errMsg_);
            }
            if (code_ != 0) {
                output.writeInt32(10, code_);
            }
            unknownFields.writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = memoizedSize;
            if (size != -1) return size;

            size = 0;
            for (int i = 0; i < collectionRes_.size(); i++) {
                size += com.google.protobuf.CodedOutputStream
                        .computeMessageSize(7, collectionRes_.get(i));
            }
            if (updateRow_ != 0) {
                size += com.google.protobuf.CodedOutputStream
                        .computeInt32Size(8, updateRow_);
            }
            if (!getErrMsgBytes().isEmpty()) {
                size += com.google.protobuf.GeneratedMessageV3.computeStringSize(9, errMsg_);
            }
            if (code_ != 0) {
                size += com.google.protobuf.CodedOutputStream
                        .computeInt32Size(10, code_);
            }
            size += unknownFields.getSerializedSize();
            memoizedSize = size;
            return size;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof S2C)) {
                return super.equals(obj);
            }
            S2C other = (S2C) obj;

            if (!getCollectionResList()
                    .equals(other.getCollectionResList())) return false;
            if (getUpdateRow()
                    != other.getUpdateRow()) return false;
            if (!getErrMsg()
                    .equals(other.getErrMsg())) return false;
            if (getCode()
                    != other.getCode()) return false;
            if (!unknownFields.equals(other.unknownFields)) return false;
            return true;
        }

        @Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptor().hashCode();
            if (getCollectionResCount() > 0) {
                hash = (37 * hash) + COLLECTIONRES_FIELD_NUMBER;
                hash = (53 * hash) + getCollectionResList().hashCode();
            }
            hash = (37 * hash) + UPDATEROW_FIELD_NUMBER;
            hash = (53 * hash) + getUpdateRow();
            hash = (37 * hash) + ERRMSG_FIELD_NUMBER;
            hash = (53 * hash) + getErrMsg().hashCode();
            hash = (37 * hash) + CODE_FIELD_NUMBER;
            hash = (53 * hash) + getCode();
            hash = (29 * hash) + unknownFields.hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static S2C parseFrom(
                java.nio.ByteBuffer data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static S2C parseFrom(
                java.nio.ByteBuffer data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static S2C parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static S2C parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static S2C parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static S2C parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static S2C parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static S2C parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static S2C parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input);
        }

        public static S2C parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static S2C parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static S2C parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(S2C prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE
                    ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(
                BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        /**
         * Protobuf type {@code io.jopen.snack.common.protol.S2C}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
                // @@protoc_insertion_point(builder_implements:io.jopen.snack.common.protol.S2C)
                S2COrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return RpcData.internal_static_io_jopen_snack_common_protol_S2C_descriptor;
            }

            @Override
            protected FieldAccessorTable
            internalGetFieldAccessorTable() {
                return RpcData.internal_static_io_jopen_snack_common_protol_S2C_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                S2C.class, Builder.class);
            }

            // Construct using io.jopen.snack.common.protol.RpcData.S2C.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessageV3
                        .alwaysUseFieldBuilders) {
                    getCollectionResFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (collectionResBuilder_ == null) {
                    collectionRes_ = java.util.Collections.emptyList();
                    bitField0_ = (bitField0_ & ~0x00000001);
                } else {
                    collectionResBuilder_.clear();
                }
                updateRow_ = 0;

                errMsg_ = "";

                code_ = 0;

                return this;
            }

            @Override
            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return RpcData.internal_static_io_jopen_snack_common_protol_S2C_descriptor;
            }

            @Override
            public S2C getDefaultInstanceForType() {
                return S2C.getDefaultInstance();
            }

            @Override
            public S2C build() {
                S2C result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public S2C buildPartial() {
                S2C result = new S2C(this);
                int from_bitField0_ = bitField0_;
                if (collectionResBuilder_ == null) {
                    if (((bitField0_ & 0x00000001) != 0)) {
                        collectionRes_ = java.util.Collections.unmodifiableList(collectionRes_);
                        bitField0_ = (bitField0_ & ~0x00000001);
                    }
                    result.collectionRes_ = collectionRes_;
                } else {
                    result.collectionRes_ = collectionResBuilder_.build();
                }
                result.updateRow_ = updateRow_;
                result.errMsg_ = errMsg_;
                result.code_ = code_;
                onBuilt();
                return result;
            }

            @Override
            public Builder clone() {
                return super.clone();
            }

            @Override
            public Builder setField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return super.setField(field, value);
            }

            @Override
            public Builder clearField(
                    com.google.protobuf.Descriptors.FieldDescriptor field) {
                return super.clearField(field);
            }

            @Override
            public Builder clearOneof(
                    com.google.protobuf.Descriptors.OneofDescriptor oneof) {
                return super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    int index, Object value) {
                return super.setRepeatedField(field, index, value);
            }

            @Override
            public Builder addRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return super.addRepeatedField(field, value);
            }

            @Override
            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof S2C) {
                    return mergeFrom((S2C) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(S2C other) {
                if (other == S2C.getDefaultInstance()) return this;
                if (collectionResBuilder_ == null) {
                    if (!other.collectionRes_.isEmpty()) {
                        if (collectionRes_.isEmpty()) {
                            collectionRes_ = other.collectionRes_;
                            bitField0_ = (bitField0_ & ~0x00000001);
                        } else {
                            ensureCollectionResIsMutable();
                            collectionRes_.addAll(other.collectionRes_);
                        }
                        onChanged();
                    }
                } else {
                    if (!other.collectionRes_.isEmpty()) {
                        if (collectionResBuilder_.isEmpty()) {
                            collectionResBuilder_.dispose();
                            collectionResBuilder_ = null;
                            collectionRes_ = other.collectionRes_;
                            bitField0_ = (bitField0_ & ~0x00000001);
                            collectionResBuilder_ =
                                    com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                                            getCollectionResFieldBuilder() : null;
                        } else {
                            collectionResBuilder_.addAllMessages(other.collectionRes_);
                        }
                    }
                }
                if (other.getUpdateRow() != 0) {
                    setUpdateRow(other.getUpdateRow());
                }
                if (!other.getErrMsg().isEmpty()) {
                    errMsg_ = other.errMsg_;
                    onChanged();
                }
                if (other.getCode() != 0) {
                    setCode(other.getCode());
                }
                this.mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                S2C parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (S2C) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private int bitField0_;

            private java.util.List<com.google.protobuf.Any> collectionRes_ =
                    java.util.Collections.emptyList();

            private void ensureCollectionResIsMutable() {
                if (!((bitField0_ & 0x00000001) != 0)) {
                    collectionRes_ = new java.util.ArrayList<com.google.protobuf.Any>(collectionRes_);
                    bitField0_ |= 0x00000001;
                }
            }

            private com.google.protobuf.RepeatedFieldBuilderV3<
                    com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder> collectionResBuilder_;

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public java.util.List<com.google.protobuf.Any> getCollectionResList() {
                if (collectionResBuilder_ == null) {
                    return java.util.Collections.unmodifiableList(collectionRes_);
                } else {
                    return collectionResBuilder_.getMessageList();
                }
            }

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public int getCollectionResCount() {
                if (collectionResBuilder_ == null) {
                    return collectionRes_.size();
                } else {
                    return collectionResBuilder_.getCount();
                }
            }

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public com.google.protobuf.Any getCollectionRes(int index) {
                if (collectionResBuilder_ == null) {
                    return collectionRes_.get(index);
                } else {
                    return collectionResBuilder_.getMessage(index);
                }
            }

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public Builder setCollectionRes(
                    int index, com.google.protobuf.Any value) {
                if (collectionResBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureCollectionResIsMutable();
                    collectionRes_.set(index, value);
                    onChanged();
                } else {
                    collectionResBuilder_.setMessage(index, value);
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public Builder setCollectionRes(
                    int index, com.google.protobuf.Any.Builder builderForValue) {
                if (collectionResBuilder_ == null) {
                    ensureCollectionResIsMutable();
                    collectionRes_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    collectionResBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public Builder addCollectionRes(com.google.protobuf.Any value) {
                if (collectionResBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureCollectionResIsMutable();
                    collectionRes_.add(value);
                    onChanged();
                } else {
                    collectionResBuilder_.addMessage(value);
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public Builder addCollectionRes(
                    int index, com.google.protobuf.Any value) {
                if (collectionResBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureCollectionResIsMutable();
                    collectionRes_.add(index, value);
                    onChanged();
                } else {
                    collectionResBuilder_.addMessage(index, value);
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public Builder addCollectionRes(
                    com.google.protobuf.Any.Builder builderForValue) {
                if (collectionResBuilder_ == null) {
                    ensureCollectionResIsMutable();
                    collectionRes_.add(builderForValue.build());
                    onChanged();
                } else {
                    collectionResBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public Builder addCollectionRes(
                    int index, com.google.protobuf.Any.Builder builderForValue) {
                if (collectionResBuilder_ == null) {
                    ensureCollectionResIsMutable();
                    collectionRes_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    collectionResBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public Builder addAllCollectionRes(
                    Iterable<? extends com.google.protobuf.Any> values) {
                if (collectionResBuilder_ == null) {
                    ensureCollectionResIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(
                            values, collectionRes_);
                    onChanged();
                } else {
                    collectionResBuilder_.addAllMessages(values);
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public Builder clearCollectionRes() {
                if (collectionResBuilder_ == null) {
                    collectionRes_ = java.util.Collections.emptyList();
                    bitField0_ = (bitField0_ & ~0x00000001);
                    onChanged();
                } else {
                    collectionResBuilder_.clear();
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public Builder removeCollectionRes(int index) {
                if (collectionResBuilder_ == null) {
                    ensureCollectionResIsMutable();
                    collectionRes_.remove(index);
                    onChanged();
                } else {
                    collectionResBuilder_.remove(index);
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public com.google.protobuf.Any.Builder getCollectionResBuilder(
                    int index) {
                return getCollectionResFieldBuilder().getBuilder(index);
            }

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public com.google.protobuf.AnyOrBuilder getCollectionResOrBuilder(
                    int index) {
                if (collectionResBuilder_ == null) {
                    return collectionRes_.get(index);
                } else {
                    return collectionResBuilder_.getMessageOrBuilder(index);
                }
            }

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public java.util.List<? extends com.google.protobuf.AnyOrBuilder>
            getCollectionResOrBuilderList() {
                if (collectionResBuilder_ != null) {
                    return collectionResBuilder_.getMessageOrBuilderList();
                } else {
                    return java.util.Collections.unmodifiableList(collectionRes_);
                }
            }

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public com.google.protobuf.Any.Builder addCollectionResBuilder() {
                return getCollectionResFieldBuilder().addBuilder(
                        com.google.protobuf.Any.getDefaultInstance());
            }

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public com.google.protobuf.Any.Builder addCollectionResBuilder(
                    int index) {
                return getCollectionResFieldBuilder().addBuilder(
                        index, com.google.protobuf.Any.getDefaultInstance());
            }

            /**
             * <code>repeated .google.protobuf.Any collectionRes = 7;</code>
             */
            public java.util.List<com.google.protobuf.Any.Builder>
            getCollectionResBuilderList() {
                return getCollectionResFieldBuilder().getBuilderList();
            }

            private com.google.protobuf.RepeatedFieldBuilderV3<
                    com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder>
            getCollectionResFieldBuilder() {
                if (collectionResBuilder_ == null) {
                    collectionResBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
                            com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder>(
                            collectionRes_,
                            ((bitField0_ & 0x00000001) != 0),
                            getParentForChildren(),
                            isClean());
                    collectionRes_ = null;
                }
                return collectionResBuilder_;
            }

            private int updateRow_;

            /**
             * <code>int32 updateRow = 8;</code>
             *
             * @return The updateRow.
             */
            public int getUpdateRow() {
                return updateRow_;
            }

            /**
             * <code>int32 updateRow = 8;</code>
             *
             * @param value The updateRow to set.
             * @return This builder for chaining.
             */
            public Builder setUpdateRow(int value) {

                updateRow_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>int32 updateRow = 8;</code>
             *
             * @return This builder for chaining.
             */
            public Builder clearUpdateRow() {

                updateRow_ = 0;
                onChanged();
                return this;
            }

            private Object errMsg_ = "";

            /**
             * <code>string errMsg = 9;</code>
             *
             * @return The errMsg.
             */
            public String getErrMsg() {
                Object ref = errMsg_;
                if (!(ref instanceof String)) {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    String s = bs.toStringUtf8();
                    errMsg_ = s;
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>string errMsg = 9;</code>
             *
             * @return The bytes for errMsg.
             */
            public com.google.protobuf.ByteString
            getErrMsgBytes() {
                Object ref = errMsg_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (String) ref);
                    errMsg_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }

            /**
             * <code>string errMsg = 9;</code>
             *
             * @param value The errMsg to set.
             * @return This builder for chaining.
             */
            public Builder setErrMsg(
                    String value) {
                if (value == null) {
                    throw new NullPointerException();
                }

                errMsg_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>string errMsg = 9;</code>
             *
             * @return This builder for chaining.
             */
            public Builder clearErrMsg() {

                errMsg_ = getDefaultInstance().getErrMsg();
                onChanged();
                return this;
            }

            /**
             * <code>string errMsg = 9;</code>
             *
             * @param value The bytes for errMsg to set.
             * @return This builder for chaining.
             */
            public Builder setErrMsgBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                checkByteStringIsUtf8(value);

                errMsg_ = value;
                onChanged();
                return this;
            }

            private int code_;

            /**
             * <code>int32 code = 10;</code>
             *
             * @return The code.
             */
            public int getCode() {
                return code_;
            }

            /**
             * <code>int32 code = 10;</code>
             *
             * @param value The code to set.
             * @return This builder for chaining.
             */
            public Builder setCode(int value) {

                code_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>int32 code = 10;</code>
             *
             * @return This builder for chaining.
             */
            public Builder clearCode() {

                code_ = 0;
                onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.mergeUnknownFields(unknownFields);
            }


            // @@protoc_insertion_point(builder_scope:io.jopen.snack.common.protol.S2C)
        }

        // @@protoc_insertion_point(class_scope:io.jopen.snack.common.protol.S2C)
        private static final S2C DEFAULT_INSTANCE;

        static {
            DEFAULT_INSTANCE = new S2C();
        }

        public static S2C getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        private static final com.google.protobuf.Parser<S2C>
                PARSER = new com.google.protobuf.AbstractParser<S2C>() {
            @Override
            public S2C parsePartialFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return new S2C(input, extensionRegistry);
            }
        };

        public static com.google.protobuf.Parser<S2C> parser() {
            return PARSER;
        }

        @Override
        public com.google.protobuf.Parser<S2C> getParserForType() {
            return PARSER;
        }

        @Override
        public S2C getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

    }

    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_io_jopen_snack_common_protol_C2S_descriptor;
    private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_io_jopen_snack_common_protol_C2S_fieldAccessorTable;
    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_io_jopen_snack_common_protol_S2C_descriptor;
    private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_io_jopen_snack_common_protol_S2C_fieldAccessorTable;

    public static com.google.protobuf.Descriptors.FileDescriptor
    getDescriptor() {
        return descriptor;
    }

    private static com.google.protobuf.Descriptors.FileDescriptor
            descriptor;

    static {
        String[] descriptorData = {
                "\n\023rpcExpression.proto\022\034io.jopen.snack.co" +
                        "mmon.protol\032\022protobuf/any.proto\"\334\004\n\003C2S\022" +
                        "(\n\nconditions\030\001 \003(\0132\024.google.protobuf.An" +
                        "y\022\"\n\004rows\030\002 \003(\0132\024.google.protobuf.Any\022H\n" +
                        "\016operationLevel\030\003 \001(\01620.io.jopen.snack.c" +
                        "ommon.protol.C2S.OperationLevel\022B\n\013dbOpe" +
                        "ration\030\004 \001(\0162-.io.jopen.snack.common.pro" +
                        "tol.C2S.DBOperation\022H\n\016tableOperation\030\005 " +
                        "\001(\01620.io.jopen.snack.common.protol.C2S.T" +
                        "ableOperation\022A\n\trowOption\030\006 \001(\0162..io.jo" +
                        "pen.snack.common.protol.C2S.RowOperation" +
                        "\"2\n\016OperationLevel\022\014\n\010database\020\000\022\t\n\005tabl" +
                        "e\020\001\022\007\n\003row\020\002\"5\n\013DBOperation\022\014\n\010createDB\020" +
                        "\000\022\n\n\006dropDB\020\001\022\014\n\010modifyDB\020\002\"A\n\016TableOper" +
                        "ation\022\017\n\013createTable\020\000\022\r\n\tdropTable\020\001\022\017\n" +
                        "\013modifyTable\020\002\">\n\014RowOperation\022\n\n\006SELECT" +
                        "\020\000\022\n\n\006UPDATE\020\001\022\n\n\006INSERT\020\002\022\n\n\006DELETE\020\004\"c" +
                        "\n\003S2C\022+\n\rcollectionRes\030\007 \003(\0132\024.google.pr" +
                        "otobuf.Any\022\021\n\tupdateRow\030\010 \001(\005\022\016\n\006errMsg\030" +
                        "\t \001(\t\022\014\n\004code\030\n \001(\005B\tB\007RpcDatab\006proto3"
        };
        descriptor = com.google.protobuf.Descriptors.FileDescriptor
                .internalBuildGeneratedFileFrom(descriptorData,
                        new com.google.protobuf.Descriptors.FileDescriptor[]{
                                com.google.protobuf.AnyProto.getDescriptor(),
                        });
        internal_static_io_jopen_snack_common_protol_C2S_descriptor =
                getDescriptor().getMessageTypes().get(0);
        internal_static_io_jopen_snack_common_protol_C2S_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_io_jopen_snack_common_protol_C2S_descriptor,
                new String[]{"Conditions", "Rows", "OperationLevel", "DbOperation", "TableOperation", "RowOption",});
        internal_static_io_jopen_snack_common_protol_S2C_descriptor =
                getDescriptor().getMessageTypes().get(1);
        internal_static_io_jopen_snack_common_protol_S2C_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_io_jopen_snack_common_protol_S2C_descriptor,
                new String[]{"CollectionRes", "UpdateRow", "ErrMsg", "Code",});
        com.google.protobuf.AnyProto.getDescriptor();
    }

    // @@protoc_insertion_point(outer_class_scope)
}
